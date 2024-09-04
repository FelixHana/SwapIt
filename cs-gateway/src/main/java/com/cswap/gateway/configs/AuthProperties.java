package com.cswap.gateway.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZCY-
 */
@Data
@Component
@ConfigurationProperties(prefix = "cs.auth")
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
}
