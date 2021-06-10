package com.iddera.userprofile.api.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "zoom")
@Configuration
public class ZoomConfig {
    private String apiKey;
    private String apiSecret;
    private Long tokenExpiryDuration;
    private String baseUrl;
}
