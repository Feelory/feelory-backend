package com.feelory.feelory_backend.words.dto.model;

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
public class DailyWord {
    private Long id;
    private WordSummary word;
    private LocalDateTime topicDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static DailyWord fromEntity(DailyWords dailyWords) {

        Word word = Word.fromEntity(dailyWords.getWord());
        WordSummary summaryDto = WordSummary.fromDto(word);

        return DailyWord.builder()
                .id(dailyWords.getId())
                .word(summaryDto)
                .topicDate(dailyWords.getTopicDate())
                .description(dailyWords.getDescription())
                .createdAt(dailyWords.getCreatedAt())
                .updatedAt(dailyWords.getUpdatedAt())
                .isActive(dailyWords.getIsActive())
                .build();
    }
}
