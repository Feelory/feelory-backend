package com.feelory.feelory_backend.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final WebclientProperties webclientProperties;

    @Bean
    public WebClient geminiWebClient() {
        System.out.println(webclientProperties.getApikey());
        return WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("X-goog-api-key",webclientProperties.getApikey())
                .build();
    }
}
