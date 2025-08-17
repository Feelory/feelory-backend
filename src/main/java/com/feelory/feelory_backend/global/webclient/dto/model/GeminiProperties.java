package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "llm.gemini")
public class GeminiProperties {
    private String apikey;
    private GeminiModel model;
}
