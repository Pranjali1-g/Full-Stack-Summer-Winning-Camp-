package com.cineverse.gateway.config;

import com.cineverse.gateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter authFilter) {
        return builder.routes()
                // Public Path: Auth Service Route (Port 8081)
                .route("auth-service-route", r -> r.path("/auth/**")
                        .uri("http://localhost:8081"))
                
                // Secure Path: Movie Service Route (Port 8082) - Authenticated via custom Filter
                .route("movie-service-route", r -> r.path("/api/movies/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8082"))
                .build();
    }
}