package com.samyam.etl.demo.ApiGateway.Configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 02/12/2024, Monday
 **/
@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/**").uri("lb://DATAEXTRACTIONSERVICE"))
                .build();
    }
}
