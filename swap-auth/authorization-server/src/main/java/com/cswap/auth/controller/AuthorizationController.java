package com.cswap.auth.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTPayload;
import com.alibaba.nacos.api.model.v2.Result;
import com.cswap.auth.config.CustomAuthenticationEntryPoint;
import com.cswap.auth.domain.dto.UserRegDto;
import com.cswap.auth.domain.enums.UserCodeEnum;
import com.cswap.auth.domain.po.SysBaseUser;
import com.cswap.auth.exception.AuthCodeEnum;
import com.cswap.auth.exception.AuthException;
import com.cswap.auth.exception.UserException;
import com.cswap.auth.service.ISysBaseUserService;
import com.cswap.common.constant.CaptchaConstants;
import com.cswap.common.constant.SecurityConstants;
import com.cswap.common.utils.JwtUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ZCY-
 */
@Tag(name = "授权接口")
@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final RedisTemplate<String, Object> redisTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ISysBaseUserService sysBaseUserService;


    @DeleteMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        JWTPayload jwtPayload = JwtUtil.parsePayload(bearerToken);
        if (jwtPayload == null) {
            throw new AuthException(AuthCodeEnum.JWT_INVALID);
        }
        String jti = jwtPayload.getClaimsJson().getStr("jti"); // JWT唯一标识
        long exp = jwtPayload.getClaimsJson().getLong("exp"); // JWT过期时间戳

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) { // 已过期
            throw new AuthException(AuthCodeEnum.JWT_EXPIRED);
        }
        redisTemplate.opsForValue().set(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return true;
    }

    @PostMapping("/register")
    public String register(@Validated @RequestBody UserRegDto userRegDto) {
        // 校验验证码
        /*String captchaKey = userRegDto.getCaptchaKey();
        String captchaCode = userRegDto.getCaptchaCode();
        if (captchaKey == null || captchaCode == null) {
            throw new AuthException(AuthCodeEnum.CAPTCHA_PARAM_ERROR);
        }
        Boolean result = restTemplate.postForObject(CaptchaConstants.CAPTCHA_SERVICE_VERIFY_URL + "?key=" + captchaKey + "&code=" + captchaCode, null, Boolean.class);
        if (Boolean.FALSE.equals(result)) {
            throw new AuthException(AuthCodeEnum.CAPTCHA_ERROR);
        }*/
        // 校验密码一致
        if (!userRegDto.getPassword().equals(userRegDto.getConfirmPassword())) {
            throw new UserException(UserCodeEnum.PASSWORD_CONFIRM_ERROR);
        }
        // 校验用户名并注册
        sysBaseUserService.register(userRegDto);
        return "success";
    }
}
