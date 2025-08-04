package com.example.sistemalogin;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.example.sistemalogin.service.AuthService;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public AuthService authService() {
        return new AuthService();
    }
} 