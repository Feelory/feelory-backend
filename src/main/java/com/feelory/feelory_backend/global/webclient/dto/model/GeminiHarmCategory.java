package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
    참고 : https://ai.google.dev/api/generate-content?hl=ko#v1beta.HarmCategory
*/
@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiHarmCategory {
    HARM_CATEGORY_UNSPECIFIED("카테고리가 지정되지 않았습니다."),
    HARM_CATEGORY_DEROGATORY("PaLM - ID 또는 보호 속성을 대상으로 하는 부정적이거나 유해한 댓글"),
    HARM_CATEGORY_TOXICITY("PaLM - 무례하거나 모욕적이거나 욕설이 있는 콘텐츠"),
    HARM_CATEGORY_VIOLENCE("PaLM - 개인 또는 그룹에 대한 폭력을 묘사하는 시나리오 또는 유혈 콘텐츠에 대한 일반적인 설명을 묘사합니다."),
    HARM_CATEGORY_SEXUAL("PaLM - 성행위 또는 기타 외설적인 콘텐츠에 대한 참조가 포함되어 있습니다."),
    HARM_CATEGORY_MEDICAL("PaLM - 확인되지 않은 의학적 조언을 홍보합니다."),
    HARM_CATEGORY_DANGEROUS("PaLM - 유해한 행위를 조장, 촉진 또는 장려하는 위험한 콘텐츠입니다."),
    HARM_CATEGORY_HARASSMENT("Gemini - 괴롭힘 콘텐츠"),
    HARM_CATEGORY_HATE_SPEECH("Gemini - 증오심 표현 및 콘텐츠"),
    HARM_CATEGORY_SEXUALLY_EXPLICIT("Gemini - 음란물"),
    HARM_CATEGORY_DANGEROUS_CONTENT("Gemini - 위험한 콘텐츠");

    private final String description;
}
