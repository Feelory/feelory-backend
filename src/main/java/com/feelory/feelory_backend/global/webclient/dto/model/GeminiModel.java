package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum GeminiModel {
    GEMINI_20_FLASH_LITE("gemini-2.0-flash-lite","비용 효율성 및 짧은 지연 시간"),
    GEMINI_20_FLASH("gemini-2.0-flash","차세대 기능, 속도, 실시간 스트리밍"),
    GEMINI_25_FLASH_LITE("gemini-2.5-flash-lite","대용량 작업을 지원하는 가장 비용 효율적인 모델"),
    GEMINI_25_FLASH("gemini-2.5-flash","적응적 사고, 비용 효율성"),
    GEMINI_25_PRO("gemini-2.5-pro","향상된 사고 및 추론, 멀티모달 이해, 고급 코딩 등");

    private final String modelName;
    private final String description;
}

