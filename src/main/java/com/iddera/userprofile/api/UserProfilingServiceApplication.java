package com.iddera.userprofile.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class UserProfilingServiceApplication {
    public static void main(String... args) {
        SpringApplication.run(UserProfilingServiceApplication.class, args);
    }
}
