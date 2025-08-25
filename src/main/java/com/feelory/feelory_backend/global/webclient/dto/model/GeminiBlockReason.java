package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
    참고 : https://ai.google.dev/api/generate-content?hl=ko#BlockReason
*/
@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiBlockReason {
    BLOCK_REASON_UNSPECIFIED("기본값 이 값은 사용되지 않습니다."),
    SAFETY("안전상의 이유로 프롬프트가 차단되었습니다. safetyRatings를 검사하여 차단한 안전 카테고리를 파악합니다."),
    OTHER("알 수 없는 이유로 프롬프트가 차단되었습니다."),
    BLOCKLIST("용어 차단 목록에 포함된 용어로 인해 프롬프트가 차단되었습니다."),
    PROHIBITED_CONTENT("금지된 콘텐츠로 인해 프롬프트가 차단되었습니다."),
    IMAGE_SAFETY("안전하지 않은 이미지 생성 콘텐츠로 인해 후보자가 차단되었습니다.");

    private final String description;
}
