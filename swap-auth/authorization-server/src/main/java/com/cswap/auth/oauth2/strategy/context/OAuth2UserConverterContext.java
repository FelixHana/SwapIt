package com.cswap.auth.oauth2.strategy.context;

import cn.hutool.core.util.StrUtil;
import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import com.cswap.auth.oauth2.strategy.OAuth2UserConverterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2UserConverterContext {
    private final Map<String, OAuth2UserConverterStrategy> userConverterStrategyMap;

    public OAuth2UserConverterStrategy getInstance(String type) {
        if (StrUtil.isEmpty(type)) {

            throw new UnsupportedOperationException("登录方式不能为空.");
        }
        OAuth2UserConverterStrategy userConverterStrategy = userConverterStrategyMap.get(type);
        if (userConverterStrategy == null) {
            throw new UnsupportedOperationException("不支持[" + type + "]登录方式获取用户信息转换器");
        }
        return userConverterStrategy;
    }

    public SysOAuth2ThirdAccount convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
        // 获取三方登录配置的registrationId，这里将他当做登录方式
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 转换用户信息
        SysOAuth2ThirdAccount oAuth2ThirdAccount = this.getInstance(registrationId).convert(oAuth2User);
        // 获取AccessToken
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        // 设置token
        oAuth2ThirdAccount.setCredential(accessToken.getTokenValue());
        // 设置账号的方式
        oAuth2ThirdAccount.setPlatform(registrationId);
        Instant expiresAt = accessToken.getExpiresAt();
        if (oAuth2ThirdAccount.getCredentialExpireTime() != null && expiresAt != null) {
            LocalDateTime tokenExpiresAt = expiresAt.atZone(ZoneId.of("UTC")).toLocalDateTime();
            // token过期时间
            oAuth2ThirdAccount.setCredentialExpireTime(tokenExpiresAt);
        }
        return oAuth2ThirdAccount;
    }
}
