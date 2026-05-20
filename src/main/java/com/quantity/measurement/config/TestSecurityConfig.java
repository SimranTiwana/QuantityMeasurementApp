package com.quantity.measurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// =============================================================
// TEST SECURITY CONFIG
// =============================================================
// Active only in the 'test' profile.
// Permits all requests so that @SpringBootTest integration
// tests can call REST endpoints without setting up OAuth2 /
// JWT token infrastructure.  The production SecurityConfig
// bean is suppressed when @Profile("test") is active.
// =============================================================

@Configuration
@EnableWebSecurity
@Profile("test")

public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll()
                );

        return http.build();
    }
}