package com.feelory.feelory_backend.global.webclient;

import com.feelory.feelory_backend.global.webclient.dto.request.GenerateContentRequest;
import com.feelory.feelory_backend.global.webclient.dto.reponse.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GenerateContent {

    private final WebClient geminiWebClient;

    public GenerateContentResponse generate(GenerateContentRequest request) {
        return geminiWebClient.post()
                .uri("/v1beta/models/gemini-2.5-flash:generateContent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateContentResponse.class)
                .block();
    }
}
