package com.feelory.feelory_backend.global.webclient;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final GeminiProperties geminiProperties;

    @Bean
    public WebClient geminiWebClient() {
        String modelName = geminiProperties.getModel().getModelName();

        return WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("X-goog-api-key", geminiProperties.getApikey())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
