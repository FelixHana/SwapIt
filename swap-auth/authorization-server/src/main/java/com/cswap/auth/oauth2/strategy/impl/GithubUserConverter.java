package com.cswap.auth.oauth2.strategy.impl;

import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import com.cswap.auth.oauth2.strategy.OAuth2UserConverterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ZCY-
 */
@Component("github")
@RequiredArgsConstructor
public class GithubUserConverter implements OAuth2UserConverterStrategy {
    /**
     * 转换 OAuth2User 信息到 {@link SysOAuth2ThirdAccount}
     * @param oAuth2User
     * @return
     */
    @Override
    public SysOAuth2ThirdAccount convert(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        SysOAuth2ThirdAccount oAuth2ThirdAccount = new SysOAuth2ThirdAccount();
        oAuth2ThirdAccount
                // nameAttrId = "id"
                .setUniqueId(oAuth2User.getName())
                .setName(oAuth2User.getAttribute("name"))
                .setAvatarUrl(oAuth2User.getAttribute("avatar_url"))
                .setCredentialExpireTime(null);
        return oAuth2ThirdAccount;
    }
}
