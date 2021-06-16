package com.iddera.userprofile.api.app.config;

import com.iddera.client.provider.RetrofitProvider;
import com.iddera.usermanagement.client.UserManagementClient;
import com.iddera.usermanagement.client.endpoints.Users;
import com.iddera.userprofile.api.domain.consultation.zoom.service.ZoomClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ClientConfig {

    private final ClientProperties config;
    private final ZoomConfig zoomConfig;

    @Bean
    public UserManagementClient userManagementClient() {
        RetrofitProvider retrofitProvider = new RetrofitProvider(config.getConnectionTimeOut(), config.getReadTimeOut(), config.getWriteTimeOut());
        return new UserManagementClient(retrofitProvider, config.getUserManagementUrl());
    }

    @Bean
    public Users users() {
        return userManagementClient().users();
    }
    
    @Bean
    public ZoomClient zoomClient() {
        RetrofitProvider retrofitProvider = new RetrofitProvider(300, 30, 30);
        return retrofitProvider.initializer(ZoomClient.class, zoomConfig.getBaseUrl());
    }
}
