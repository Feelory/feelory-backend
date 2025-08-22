package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
    참고 : https://ai.google.dev/api/generate-content?hl=ko#HarmProbability
*/
@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiHarmProbability {

    HARM_PROBABILITY_UNSPECIFIED("확률이 지정되지 않았습니다."),
    NEGLIGIBLE("콘텐츠가 안전하지 않을 가능성은 무시할 만합니다."),
    LOW("콘텐츠는 안전하지 않을 가능성이 낮습니다."),
    MEDIUM("콘텐츠가 안전하지 않을 가능성이 중간 정도입니다."),
    HIGH("콘텐츠가 안전하지 않을 가능성이 높습니다.");

    private final String description;
}
