package com.quantity.measurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler
            oAuth2LoginSuccessHandler;

    private final JwtAuthenticationFilter
            jwtAuthenticationFilter;

    public SecurityConfig(
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {

        this.oAuth2LoginSuccessHandler =
                oAuth2LoginSuccessHandler;

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/oauth2/**",
                                "/login/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/v1/measurements/**"
                        ).authenticated()

                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth -> oauth
                        .successHandler(
                                oAuth2LoginSuccessHandler
                        )
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}