package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.DailyWords;
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
    private WordDto word;
    private LocalDateTime topicDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static DailyWordDto fromEntity(DailyWords dailyWords) {

        WordDto word = WordDto.fromEntity(dailyWords.getWord());

        return DailyWordDto.builder()
                .id(dailyWords.getId())
                .word(word)
                .topicDate(dailyWords.getTopicDate())
                .description(dailyWords.getDescription())
                .createdAt(dailyWords.getCreatedAt())
                .updatedAt(dailyWords.getUpdatedAt())
                .isActive(dailyWords.getIsActive())
                .build();
    }
}
