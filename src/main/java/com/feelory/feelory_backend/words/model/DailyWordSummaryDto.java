package com.feelory.feelory_backend.words.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWordSummaryDto {
    private Long id;
    private String category;
    private String word;
    private String description;

    public static DailyWordSummaryDto fromDto(DailyWordDto dto) {
        WordSummaryDto word = dto.getWord();
        CategorySummaryDto category = word.getCategory();

        return DailyWordSummaryDto.builder()
                .id(dto.getId())
                .category(category.getName())
                .word(word.getName())
                .description(dto.getDescription())
                .build();
    }
}
