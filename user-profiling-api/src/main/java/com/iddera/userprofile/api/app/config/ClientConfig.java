package com.iddera.userprofile.api.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "iddera.client")
public class ClientConfig {
    private Integer readTimeOut = 40;
    private Integer connectionTimeOut = 40;
    private Integer writeTimeOut = 40;
    private String userManagementUrl;
}
