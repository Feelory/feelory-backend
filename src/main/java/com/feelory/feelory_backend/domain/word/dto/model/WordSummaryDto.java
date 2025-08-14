package com.feelory.feelory_backend.domain.word.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordSummaryDto {
    private Long id;
    private CategorySummaryDto category;
    private String name;
    private String description;

    public static WordSummaryDto fromDto(WordDto dto) {

        return WordSummaryDto.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
