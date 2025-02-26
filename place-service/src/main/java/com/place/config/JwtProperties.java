package com.place.config;

import lombok.Data;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZCY-
 */
@Data
@Component
@ConfigurationProperties(prefix = "pe.jwt")
public class JwtProperties {
    private String header;
    private long expire;
    private String secret;
    private String iss;
}
