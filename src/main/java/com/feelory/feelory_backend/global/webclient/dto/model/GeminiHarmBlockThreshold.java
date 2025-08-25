package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
/*
    참고 : https://ai.google.dev/api/generate-content?hl=ko#HarmBlockThreshold
*/
@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiHarmBlockThreshold {

    HARM_BLOCK_THRESHOLD_UNSPECIFIED("기준점이 지정되지 않았습니다."),
    BLOCK_LOW_AND_ABOVE("무시할 수 있는 콘텐츠는 허용됩니다."),
    BLOCK_MEDIUM_AND_ABOVE("무시할 수 있는 수준 및 낮음 콘텐츠는 허용됩니다."),
    BLOCK_ONLY_HIGH("'무시할 수 있음', '낮음', '중간' 콘텐츠는 허용됩니다.");
    
    /* 권고되지 않음 */
//    BLOCK_NONE("모든 콘텐츠가 허용됩니다."),
//    OFF("안전 필터를 사용 중지합니다.");

    private final String description;
}
