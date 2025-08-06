package com.feelory.feelory_backend.words.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordSummary {
    private Long id;
    private CategorySummary category;
    private String name;
    private String description;

    public static WordSummary fromDto(Word dto) {

        return WordSummary.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
