package com.feelory.feelory_backend.words.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryDto {
    private Long id;
    private String name;
    private String description;

    public static CategorySummaryDto fromDto(CategoryDto dto) {

        return CategorySummaryDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
