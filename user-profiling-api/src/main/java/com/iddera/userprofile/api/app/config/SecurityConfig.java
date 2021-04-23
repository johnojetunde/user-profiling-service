package com.iddera.userprofile.api.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Filter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
    }
}
