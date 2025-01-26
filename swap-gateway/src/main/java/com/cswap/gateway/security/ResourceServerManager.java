package com.cswap.gateway.security;


import cn.hutool.core.collection.CollectionUtil;
import com.cswap.common.constant.GlobalConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;


import java.util.List;

/**
 * @author ZCY-
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 预检请求放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 获取请求方法及路径
        String restfulPath = request.getMethodValue() + ":" + request.getURI().getPath();
        log.info("request method:path = {}", restfulPath);
        // path 作为 key 获取缓存的允许角色集合
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        Object rolesObject = hashOperations.get(GlobalConstants.REDIS_PERM_ENDPOINT_KEY, restfulPath);
//        // 角色集为空则禁止访问
//        if (!(rolesObject instanceof List<?>)) {
//            return Mono.just(new AuthorizationDecision(false));
//        }
        List<String> allowedRoles = (List<String>) rolesObject;
        // 判断请求携带的角色是否包含能通过权限的角色
        Mono<AuthorizationDecision> authorizationDecisionMono = authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    log.info("用户权限（角色） : {}", authority);
                    if ("SYSTEM_ADMIN".equals(authority)) { // 如果是超级管理员则放行
                        return true;
                    }
                    // 用户角色中只要有一个满足则通过
                    return CollectionUtil.isNotEmpty(allowedRoles) && allowedRoles.contains(authority);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}

