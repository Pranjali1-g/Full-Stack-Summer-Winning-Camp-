package com.cineverse.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Forces Spring Boot to drop defaults and use this exact security structure
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable Cross-Site Request Forgery (CSRF) since we are building a stateless REST API
            .csrf(csrf -> csrf.disable())
            
            // 2. Configure endpoint authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register", "/auth/login").permitAll() // Explicitly permit these public endpoints
                .anyRequest().authenticated()                                // Lock everything else down
            )
            
            // 3. Disable the default basic login form window popup
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());
            
        return http.build();
    }
}