package com.feelory.feelory_backend.words.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWordUpdateRequest {
    @NotNull(message = "수정할 오늘의 단어 ID는 필수입니다.")
    private Long wordId;
    private LocalDateTime topicDate;
    private String description;
}
