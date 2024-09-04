package com.cswap.auth.oauth2.service;

import com.cswap.auth.domain.po.SysOAuth2ThirdAccount;
import com.cswap.auth.oauth2.strategy.context.OAuth2UserConverterContext;
import com.cswap.auth.service.ISysOAuth2ThirdAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2UserConverterContext userConverterContext;
    private final ISysOAuth2ThirdAccountService oAuth2ThirdAccountService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 获取第三方得到的 OAuthUser
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 转换为项目中的 SysOAuth2ThirdAccount
        SysOAuth2ThirdAccount oAuth2ThirdAccount = userConverterContext.convert(userRequest, oAuth2User);
        // 数据库验证并保存第三方用户
        String generatedPassword = oAuth2ThirdAccountService.createThirdUser(oAuth2ThirdAccount);
        // 将loginType设置至attributes
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>(oAuth2User.getAttributes());
        attributes.put("genPassword", generatedPassword);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, userNameAttributeName);
    }
}
