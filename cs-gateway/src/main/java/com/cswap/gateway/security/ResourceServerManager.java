package com.cswap.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 预检请求放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        PathMatcher pathMatcher = new AntPathMatcher(); // 【声明定义】Ant路径匹配模式，“请求路径”和缓存中权限规则的“URL权限标识”匹配
        String path = request.getURI().getPath();

        String token = request.getHeaders().getFirst("Authorization");

        // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
        String restfulPath = request.getMethodValue() + ":" + path;
        log.info("请求方法:RESTFul请求路径：{}", restfulPath);

        // 缓存取【URL权限标识->角色集合】权限规则
        //Map<String, Object> permRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);

        // 根据 “请求路径” 和 权限规则中的“URL权限标识”进行Ant匹配，得出拥有权限的角色集合
//        Set<String> hasPermissionRoles = CollectionUtil.newHashSet(); // 【声明定义】有权限的角色集合
//
//        for (Map.Entry<String, Object> permRoles : permRolesRules.entrySet()) {
//            String perm = permRoles.getKey(); // 缓存权限规则的键：URL权限标识
//            if (pathMatcher.match(perm, restfulPath)) {
//                List<String> roles = Convert.toList(String.class, permRoles.getValue()); // 缓存权限规则的值：有请求路径访问权限的角色集合
//                hasPermissionRoles.addAll(Convert.toList(String.class, roles));
//            }
//        }
        Set<String> hasPermissionRoles = CollectionUtil.newHashSet();
        // 判断用户JWT中携带的角色是否有能通过权限拦截的角色
        Mono<AuthorizationDecision> authorizationDecisionMono = authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    log.info("用户权限（角色） : {}", authority); // ROLE_ROOT
                    String role = authority;
                    if ("SYSTEM_ADMIN".equals(role)) { // 如果是超级管理员则放行
                        return true;
                    }
                    return CollectionUtil.isNotEmpty(hasPermissionRoles) && hasPermissionRoles.contains(role); // 用户角色中只要有一个满足则通过权限校验
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}

