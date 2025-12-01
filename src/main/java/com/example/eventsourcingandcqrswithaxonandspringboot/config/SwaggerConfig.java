package com.example.eventsourcingandcqrswithaxonandspringboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Event Sourcing & CQRS API")
                        .description("Bank Account Management API using Axon Framework")
                        .version("v1.0"));
    }
}
