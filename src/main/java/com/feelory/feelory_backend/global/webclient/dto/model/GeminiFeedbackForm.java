package com.feelory.feelory_backend.global.webclient.dto.model;

import com.feelory.feelory_backend.global.webclient.annotation.GeminiFeedbackProperty;
import lombok.*;

/**
 * Gemini가 JSON으로 반환해야 하는 피드백 계약 모델.
 * - @GeminiFeedbackProperty 메타데이터로 responseSchema(properties/required/order) 생성
 * - LLM 응답(JSON) 역직렬화 대상 (score, content 키 기준)
 *
 * 예시 JSON:
 * { "score": 2, "content": "간단한 피드백 본문" }
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiFeedbackForm {

    @GeminiFeedbackProperty(
            type = GeminiPropertyType.INTEGER,
            description = "평가 점수. 10점 만점 중 몇 점인지 (예: 2)",
            order = 1,
            name = "score"
    )
    private Integer score;

    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "피드백 내용",
            order = 2,
            name = "content"
    )
    private String content;
}
