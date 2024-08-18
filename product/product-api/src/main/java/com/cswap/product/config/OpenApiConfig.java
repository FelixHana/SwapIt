package com.cswap.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZCY-
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("校园二手平台 商品服务 api 文档")
                        // 接口文档描述
                        .description("CampusSwap api doc")
                        // 接口文档版本
                        .version("v1.0"));
    }
}
