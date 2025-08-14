package com.feelory.feelory_backend.word.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWordDto {
    private Long id;
    private WordSummaryDto word;
    private LocalDateTime topicDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static DailyWordDto fromEntity(com.feelory.feelory_backend.word.entity.DailyWord dailyWord) {

        WordDto word = WordDto.fromEntity(dailyWord.getWord());
        WordSummaryDto summaryDto = WordSummaryDto.fromDto(word);

        return DailyWordDto.builder()
                .id(dailyWord.getId())
                .word(summaryDto)
                .topicDate(dailyWord.getTopicDate())
                .description(dailyWord.getDescription())
                .createdAt(dailyWord.getCreatedAt())
                .updatedAt(dailyWord.getUpdatedAt())
                .isActive(dailyWord.getIsActive())
                .build();
    }
}
