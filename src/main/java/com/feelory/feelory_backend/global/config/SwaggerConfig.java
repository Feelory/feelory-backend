package com.feelory.feelory_backend.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Feelory Swagger")
                .description("Feelory API Swagger")
                .version("v1")
        );
    }
}