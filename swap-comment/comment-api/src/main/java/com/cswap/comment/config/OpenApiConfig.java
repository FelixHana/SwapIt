package com.cswap.comment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * @author ZCY-
 */
@Configuration
public class OpenApiConfig {
    @Value("${spring.security.oauth2.authorization-server.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.authorization-server.auth-uri}")
    private String authUri;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        // OAuth2 授权模式
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .flows(new OAuthFlows()
                                                .password(
                                                        new OAuthFlow()
                                                                .tokenUrl(tokenUri)
                                                                .refreshUrl(tokenUri)
                                                )
                                        )
                                        // 安全模式使用Bearer令牌（即JWT）
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // 接口全局添加 Authorization 参数
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                // 接口文档标题
                .info(new Info().title("评论服务 api 文档")
                        // 接口文档描述
                        .description("CampusSwap Comment service api doc")
                        // 接口文档版本
                        .version("v1.0"));
    }

}
