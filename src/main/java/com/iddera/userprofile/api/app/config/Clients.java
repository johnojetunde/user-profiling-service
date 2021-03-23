package com.iddera.userprofile.api.app.config;

import com.iddera.client.provider.RetrofitProvider;
import com.iddera.usermanagement.client.UserManagementClient;
import com.iddera.usermanagement.client.endpoints.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Clients {

    private final ClientConfig config;

    @Bean
    public UserManagementClient userManagementClient() {
        RetrofitProvider retrofitProvider = new RetrofitProvider(config.getConnectionTimeOut(), config.getReadTimeOut(), config.getWriteTimeOut());
        return new UserManagementClient(retrofitProvider, config.getUserManagementUrl());
    }

    @Bean
    public Users users() {
        return userManagementClient().users();
    }
}
