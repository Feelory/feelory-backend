package com.feelory.feelory_backend.global.webclient.dto.model;

import com.feelory.feelory_backend.global.webclient.annotation.GeminiFeedbackProperty;
import lombok.*;

import java.util.List;

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
            name = "score",
            minimum = 0,
            maximum = 10
    )
    private Integer score;

    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "피드백 내용",
            order = 2,
            name = "content",
            minLength = 30,
            maxLength = 5000
    )
    private String content;



    // =========== S : 질문 ===========

    @Getter(AccessLevel.NONE)
    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "글에 대한 미래 지향적인 질문좀 해줘",
            order = 3,
            name = "q1",
            minLength = 30,
            maxLength = 5000
    )
    private String q1;

    @Getter(AccessLevel.NONE)
    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "글과 관련된 경험에 대해 질문해줘",
            order = 4,
            name = "q2",
            minLength = 30,
            maxLength = 5000
    )
    private String q2;

    @Getter(AccessLevel.NONE)
    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "추천할만한 방향성에 대해 질문해줘",
            order = 5,
            name = "q3",
            minLength = 30,
            maxLength = 5000
    )
    private String q3;

    // =========== E : 질문 ===========

//    @GeminiFeedbackProperty(
//            type = GeminiPropertyType.ARRAY,
//            description = """
//                주어진 글에 대해 3가지 질문을 생성할거야.
//                생성해서 List에 담아줘
//                Q1. 글에 대한 미래 지향적인 질문좀 해줘.
//                Q2. 글과 관련된 경험에 대해 질문해줘.
//                Q3. 추천할만한 방향성에 대해 질문해줘.
//            """,
//            order = 3,
//            name = "questions",
//            minItems = 3,
//            maxItems = 3
//    )
//    private List<String> questions;

    public List<String> getQuestions() {

        return List.of(q1, q2, q3);
    }
}
