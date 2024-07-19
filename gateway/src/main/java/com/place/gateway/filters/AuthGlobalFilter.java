package com.place.gateway.filters;


import com.place.common.exception.HttpException;
import com.place.gateway.config.AuthProperties;
import com.place.gateway.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 2. 判断是否拦截
        if(isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        // 3. 获取 token
        List<String> headers = request.getHeaders().get("authorization");
        String token;
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }
        else {
            token = null;
        }
        // 4. 校验解析
        Long userId;
        try {
            userId = JwtUtil.parseUserId(token);
        } catch (UnauthorizedException e) {
            // 设置未授权 401 状态
            throw new HttpException(HttpStatus.UNAUTHORIZED);
        }
        // 5. 传递用户信息
        String userInfo = userId.toString();
        // System.out.println("userId = " + userId);
        ServerWebExchange serverWebExchange = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo))
                .build();
        // 6. 放行
        return chain.filter(serverWebExchange);
    }

    private boolean isExclude(String path) {
        for (String pattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
