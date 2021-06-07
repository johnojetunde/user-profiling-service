package com.iddera.userprofile.api.app.config;

import com.iddera.commons.utils.BearerTokenExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfig {
    @Bean
    public BearerTokenExtractor tokenExtractor() {
        return new BearerTokenExtractor();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
