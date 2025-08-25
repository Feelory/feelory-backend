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
            description = "글 속에서 묘사한 경험을 더 넓게 확장하거나 구체화하도록 유도하는 질문을 해줘(경험 확장형 질문)",
            order = 3,
            name = "q1",
            minLength = 30,
            maxLength = 5000
    )
    private String experienceExpansionQuestion;

    @Getter(AccessLevel.NONE)
    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "글쓴이가 느낀 감정이나 그 속에 담긴 가치관을 되짚어보게 하는 질문을 해줘(가치 탐색형 질문)",
            order = 4,
            name = "q2",
            minLength = 30,
            maxLength = 5000
    )
    private String valuesExplorationQuestion;

    @Getter(AccessLevel.NONE)
    @GeminiFeedbackProperty(
            type = GeminiPropertyType.STRING,
            description = "과거와 현재, 혹은 다른 장소/사람과 비교하게 하는 질문을 해줘(과거 비교형 질문)",
            order = 5,
            name = "q3",
            minLength = 30,
            maxLength = 5000
    )
    private String comparePastPresentQuestion;

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

        return List.of(
                experienceExpansionQuestion,
                valuesExplorationQuestion,
                comparePastPresentQuestion
        );
    }
}
