package com.clubedebebida.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    //filterChain
    public SecurityFilterChain filterChain(
        HttpSecurity http
    ) throws Exception {
         http.authorizeRequests(authorizeRequests ->
            authorizeRequests
                    .requestMatchers("/error/**").permitAll()
                    .requestMatchers("/garrafa/**").permitAll()
                .anyRequest().permitAll());

         return http.build();
    }
}