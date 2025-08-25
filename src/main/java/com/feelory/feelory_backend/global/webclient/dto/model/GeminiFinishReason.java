package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
    참고 : https://ai.google.dev/api/generate-content?hl=ko#FinishReason
*/
@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiFinishReason {
    FINISH_REASON_UNSPECIFIED("기본값 이 값은 사용되지 않습니다."),
    STOP("모델의 자연 중단 지점 또는 중지 시퀀스가 제공됩니다."), // 토큰 출력 성공 시 기본값
    MAX_TOKENS("요청에 지정된 최대 토큰 수에 도달했습니다."),
    SAFETY("안전상의 이유로 응답 후보 콘텐츠가 신고되었습니다."),
    RECITATION("응답 후보 콘텐츠가 암송 이유로 신고되었습니다."),
    LANGUAGE("지원되지 않는 언어를 사용한 것으로 응답 후보 콘텐츠가 신고되었습니다."),
    OTHER("알 수 없는 이유입니다."),
    BLOCKLIST("콘텐츠에 금지된 용어가 포함되어 있어 토큰 생성이 중지되었습니다."),
    PROHIBITED_CONTENT("금지된 콘텐츠가 포함되어 있을 수 있어 토큰 생성이 중지되었습니다."),
    SPII("콘텐츠에 민감한 개인 식별 정보 (SPII)가 포함되어 있을 수 있으므로 토큰 생성이 중지되었습니다."),
    MALFORMED_FUNCTION_CALL("모델에서 생성된 함수 호출이 잘못되었습니다."),
    IMAGE_SAFETY("생성된 이미지에 안전 위반이 포함되어 있어 토큰 생성이 중지되었습니다."),
    UNEXPECTED_TOOL_CALL("모델이 도구 호출을 생성했지만 요청에서 사용 설정된 도구가 없습니다.");

    private final String description;
}
