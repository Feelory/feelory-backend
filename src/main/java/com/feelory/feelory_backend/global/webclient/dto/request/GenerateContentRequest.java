package com.feelory.feelory_backend.global.webclient.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.feelory.feelory_backend.global.util.GeminiFeedbackSchemaMapper;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiHarmBlockThreshold;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiPropertyType;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiResponseType;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiHarmCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private Content systemInstruction;
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

    /*
        특정 유해 카테고리의 필터링 수준을 조절 가능한 옵션
    */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SafetySetting {
        private GeminiHarmCategory category;
        private GeminiHarmBlockThreshold threshold;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenerationConfig {
        private String responseMimeType;
        private Schema responseSchema;

        @Min(value = 0, message = "최소 값 0.0")
        @Max(value = 2, message = "최대 값 2.0")
        private Double temperature;             // 응답의 무작위성(또는 독창성)을 제어함
                                                // temperature 값이 높아질 수록 확률 분포가 뾰족해짐 -> 확률이 높은 토큰쪽으로 결정이 몰림
        private Integer topK;                   // 높은 확률 기준으로 상위 K개의 응답만을 후보군으로 둠
                                                // 권장되지 않음 -> 확률이 아닌 개수로 결정되기 때문
        private Double topP;                    // 높은 확률 기준으로 후보군의 확률의 합이 P가 될때까지의 응답을 후보군에 포함시킴

        private Integer maxOutputTokens;
        private Integer candidateCount;         // 응답 후보군 수 지정, 기본값 : 1

        private List<String> stopSequences;     // 응답 생성을 중단시키는 특정 문자열을 지정하는 옵션

        private Double presencePenalty;         // 이미 사용된 단어를 반복할 시 주는 패널티 강도
                                                // presencePenalty가 양수인 경우 -> 이미 사용된 단어(토큰)에 대한 확률을 기본값보다 더 낮춤
                                                // presencePenalty가 음수인 경우 -> 이미 사용된 단어(토큰)에 대한 확률을 기본값보다 덜 낮춤
        private Double frequencyPenalty;        // 자주 등장한 단어에 대해 부여하는 패널티 강도
                                                // presencePenalty -> 등장 / 등장 X 여부에 따라 확률이 조정됨
                                                // frequencyPenalty -> 등장 빈도에 따라 확률이 조정됨
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
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 필드 직렬화 생략
    public static class Property {
        private GeminiPropertyType type;
        private String description;
        private Integer minLength;
        private Integer maxLength;
        private Integer minimum;
        private Integer maximum;
        @JsonProperty("enum")
        private List<String> enumValue;
    }

    public static GenerateContentRequest ofText(String text) {
        Part part = new Part(text);
        Content content = new Content("user", List.of(part));

        Part personaPart = new Part("너는 이용자의 글을 읽고 평가해야하는 피드백을 주는 평가자야.");
        Part cmdPart = new Part("이용자의 글을 객관적이고 냉소적으로 평가해줘.");

        // SystemInstruction의 role 필드는 무시된다고 하니 참고바랍니다.
        Content systemInstruction = new Content(
                "system",
                List.of(
                        personaPart,
                        cmdPart
                )
        );

        Schema schema = GeminiFeedbackSchemaMapper.schema();

        GenerationConfig generationConfig = new GenerationConfig(
                "application/json",
                schema,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
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