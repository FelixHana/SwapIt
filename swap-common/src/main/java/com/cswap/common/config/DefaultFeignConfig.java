package com.cswap.common.config;

import com.cswap.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class DefaultFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 有可能没设置过 RequestContextHolder
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 获取登录用户
            String userId = request.getHeader("user-info");
            // 放入请求头中，传递给下游微服务
            requestTemplate.header("user-info", userId);
        }
    }
}
