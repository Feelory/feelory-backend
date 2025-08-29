package com.feelory.feelory_backend.domain.feedback.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QuestionType {
    EXPERIENCE_EXTEND("경험 확장형"),
    VALUE_DISCOVERY("가치 탐색형"),
    COMPARE_PAST("과거 비교형");

    private final String description;
}
