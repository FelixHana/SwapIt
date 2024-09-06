package com.cswap.gateway.filters;


import cn.hutool.jwt.JWTPayload;
import com.cswap.common.constant.SecurityConstants;
import com.cswap.common.domain.enums.exceptions.CommonCodeEnum;
import com.cswap.common.exception.CommonException;
import com.cswap.gateway.configs.AuthProperties;
import com.cswap.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author ZCY-
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 过滤器位于 JWT 和权限验证后
        ServerHttpRequest request = exchange.getRequest();
        // 添加 from-gateway 标记
        exchange.getRequest().mutate().header("from-gateway", "external").build();
        // 白名单
        if(isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        // 黑名单 JWT
        String authorization = request.getHeaders().getFirst("Authorization");

        JWTPayload payload = JwtUtil.parsePayload(authorization);
        assert payload != null;
        String jti = (String) payload.getClaim("jti");
        Boolean isBlackJwt = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (Boolean.TRUE.equals(isBlackJwt)) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "JWT is blacklisted");
        }
        // 用户认证在 security 做，无需带token
        // TODO 携带解析的用户信息
        long userId = 101L;
//        // 3. 获取 token
//        List<String> headers = request.getHeaders().get("111");
//        String token;
//        if (headers != null && !headers.isEmpty()) {
//            token = headers.get(0);
//        }
//        else {
//            throw new CommonException(CommonCodeEnum.AUTH_ERROR, "JWT is null or empty");
//        }
//         //5. 传递用户信息

        String userInfo = Long.toString(userId);
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
