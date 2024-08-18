package com.cswap.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZCY-
 */
@Data
@Component
@ConfigurationProperties(prefix = "cswap.jwt")
public class JwtProperties {
    private String header;
    private long expire;
    private String secret;
    private String iss;
}
