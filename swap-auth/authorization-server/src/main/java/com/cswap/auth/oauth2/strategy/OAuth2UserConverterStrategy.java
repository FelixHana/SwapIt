package com.cswap.auth.oauth2.strategy;

import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserConverterStrategy {
    SysOAuth2ThirdAccount convert(OAuth2User oAuth2User);
}
