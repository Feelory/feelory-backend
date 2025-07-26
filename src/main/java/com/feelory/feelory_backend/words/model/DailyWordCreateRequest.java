package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.words.entity.Words;
import jakarta.validation.constraints.NotBlank;
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
public class DailyWordCreateRequest {
    @NotNull(message = "선정할 단어 ID는 필수입니다.")
    private Long wordId;
    @NotNull(message = "날짜 지정은 필수입니다.")
    private LocalDateTime topicDate;
    private String description;
    private Boolean isReplaceApproved = false;

    public DailyWords toEntity(Words word) {
        return DailyWords.builder()
                .word(word)
                .topicDate(this.topicDate)
                .isActive(true)
                .build();
    }
}
