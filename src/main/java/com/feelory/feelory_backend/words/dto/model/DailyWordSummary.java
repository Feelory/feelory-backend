package com.feelory.feelory_backend.words.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWordSummary {
    private Long id;
    private String category;
    private String word;
    private String description;

    public static DailyWordSummary fromDto(DailyWord dto) {
        WordSummary word = dto.getWord();
        CategorySummary category = word.getCategory();

        return DailyWordSummary.builder()
                .id(dto.getId())
                .category(category.getName())
                .word(word.getName())
                .description(dto.getDescription())
                .build();
    }
}
