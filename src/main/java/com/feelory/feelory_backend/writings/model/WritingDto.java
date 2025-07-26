package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.words.model.DailyWordDto;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingDto {
    private Long id;
    private DailyWordDto dailyWord;
    private Long userId;
    private WritingGoalSummaryDto writingGoal;
    private String content;
    private Boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static WritingDto fromEntity(DailyWordWritings writings) {
        DailyWordDto dailyWord = DailyWordDto.fromEntity(writings.getDailyWord());
        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(writings.getWritingGoal());
        WritingGoalSummaryDto summary = WritingGoalSummaryDto.fromDto(writingGoal);

        return WritingDto.builder()
                .id(writings.getId())
                .dailyWord(dailyWord)
                .userId(writingGoal.getUserId())
                .writingGoal(summary)
                .content(writings.getContent())
                .visibility(writings.getVisibility())
                .createdAt(writings.getCreatedAt())
                .updatedAt(writings.getUpdatedAt())
                .isActive(writings.getIsActive())
                .build();
    }
}
