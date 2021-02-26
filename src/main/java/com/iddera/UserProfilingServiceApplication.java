package com.iddera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "com.iddera")
public class UserProfilingServiceApplication {
    public static void main(String... args) {
        SpringApplication.run(UserProfilingServiceApplication.class, args);
    }
}
