package com.iddera.userprofile.api.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "meet")
public class MeetingConfig {
    private String contactName;
    private String contactEmail;
}
