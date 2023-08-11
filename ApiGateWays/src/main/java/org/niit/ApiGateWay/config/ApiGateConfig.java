package org.niit.ApiGateWay.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGateConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(predicateSpec->predicateSpec.path("/app-v1/**").uri("lb://user-authentication-service"))
                .route(predicateSpec->predicateSpec.path("/todo-app/**").uri("lb://user-todotracker-service"))
                .route(predicateSpec->predicateSpec.path("/mail-app-v1/**").uri("lb://email-service-app"))
                .build();
    }
}
