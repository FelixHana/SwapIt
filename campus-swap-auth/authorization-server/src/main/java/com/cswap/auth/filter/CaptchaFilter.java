package com.cswap.auth.filter;

import com.cswap.auth.config.CustomAuthenticationEntryPoint;
import com.cswap.auth.exception.AuthCodeEnum;
import com.cswap.auth.exception.AuthException;
import com.cswap.common.constant.CaptchaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZCY-
 */
@Slf4j
public class CaptchaFilter extends OncePerRequestFilter {
    private final RequestMatcher requiresAuthenticationRequestMatcher;
    private final RestTemplate restTemplate = new RestTemplate();

    public CaptchaFilter(String defaultFilterProcessesUrl) {
        Assert.hasText(defaultFilterProcessesUrl, "filterProcessesUrl cannot be empty");
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(defaultFilterProcessesUrl);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthException {
        // 检验请求及拦截地址
        if (!this.requiresAuthenticationRequestMatcher.matches(request) || !request.getMethod().equals(HttpMethod.POST.toString())) {
            filterChain.doFilter(request, response);
            return;
        }
        // 检验是用户通过网关发送的请求 不是第三方认证过程中的请求
        if (!"external".equals(request.getHeader("from-gateway"))) {
            filterChain.doFilter(request, response);
            return;
        }
        // 开始校验验证码
        log.info("Authenticate captcha...");

        // 获取参数中的验证码
        try {
            String captchaKey = request.getParameter(CaptchaConstants.CAPTCHA_PARAM_KEY);
            String captchaCode = request.getParameter(CaptchaConstants.CAPTCHA_PARAM_CODE);
            if (captchaKey == null || captchaCode == null) {
                throw new AuthException(AuthCodeEnum.CAPTCHA_PARAM_ERROR);
            }
            Boolean result = restTemplate.postForObject(CaptchaConstants.CAPTCHA_SERVICE_VERIFY_URL + "?key=" + captchaKey + "&code=" + captchaCode, null, Boolean.class);
            if (Boolean.FALSE.equals(result)) {
                throw new AuthException(AuthCodeEnum.CAPTCHA_ERROR);
            }
            log.info("Captcha authenticated.");
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            SecurityContextHolder.clearContext();
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();
            customAuthenticationEntryPoint.commence(request, response, e);
        }
    }
}
