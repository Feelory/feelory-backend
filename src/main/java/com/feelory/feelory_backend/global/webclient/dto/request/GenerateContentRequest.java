package com.feelory.feelory_backend.global.webclient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.feelory.feelory_backend.global.util.GeminiFeedbackSchemaMapper;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiPropertyType;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiResponseType;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Gemini API Request DTO
 *
 * 예시 JSON
 * {
 *   "contents": [
 *     {
 *       "role": "user",
 *       "parts": [
 *         { "text": "민트초코와 가지무침의 공통점과 차이점" }
 *       ]
 *     }
 *   ],
 *   "systemInstruction": {
 *     "role": "system",
 *     "parts": [
 *       { "text": "너는 친근한 말투로 대답해." }
 *     ]
 *   },
 *   "cachedContent": "someCachedId",
 *   "tools": [
 *     {
 *       "functionDeclarations": [
 *         {
 *           "name": "getWeather",
 *           "description": "Fetch current weather",
 *           "parameters": {
 *             "type": "object",
 *             "properties": {
 *               "city": { "type": "string" }
 *             }
 *           }
 *         }
 *       ]
 *     }
 *   ],
 *   "safetySettings": [
 *     {
 *       "category": "HARM_CATEGORY_DEROGATORY",
 *       "threshold": "BLOCK_MEDIUM_AND_ABOVE"
 *     }
 *   ],
 *   "generationConfig": {
 *     "temperature": 0.7,
 *     "topK": 40,
 *     "topP": 0.95,
 *     "maxOutputTokens": 1024,
 *     "responseMimeType": "application/json"
 *   }
 * }
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateContentRequest {
    private List<Content> contents;
    private SystemInstruction systemInstruction;
    private String cachedContent;
    private List<Tool> tools;
    private List<SafetySetting> safetySettings;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private String role;
        private List<Part> parts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemInstruction {
        private String role;
        private List<Part> parts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tool {
        private List<FunctionDeclaration> functionDeclarations;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FunctionDeclaration {
        private String name;
        private String description;
        private Map<String, Object> parameters;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SafetySetting {
        private String category;
        private String threshold;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenerationConfig {
        private Double temperature;
        private Integer topK;
        private Double topP;
        private Integer maxOutputTokens;
        private String responseMimeType;
        private Schema responseSchema;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schema {
        private GeminiResponseType type;
        private boolean nullable;
        private Map<String, Property> properties;
        private List<String> propertyOrdering;
        private List<String> required;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {
        private GeminiPropertyType type;
        private String description;
        @JsonProperty("enum")
        private List<String> enumValue;
    }

    public static GenerateContentRequest ofText(String text) {
        Part part = new Part(text);
        Content content = new Content("user", List.of(part));

        Part personaPart = new Part("너는 이용자의 글을 읽고 평가해야하는 피드백을 주는 평가자야.");
        Part cmdPart = new Part("이용자의 글을 객관적이고 냉소적으로 평가해줘.");

        SystemInstruction systemInstruction = new SystemInstruction(
                "system",
                List.of(
                        personaPart,
                        cmdPart
                )
        );

        Schema schema = GeminiFeedbackSchemaMapper.schema();

        GenerationConfig generationConfig = new GenerationConfig(
                null,
                null,
                null,
                null,
                "application/json",
                schema
        );

        return new GenerateContentRequest(
                List.of(content),
                systemInstruction,
                null,
                null,
                null,
                generationConfig
        );
    }
}