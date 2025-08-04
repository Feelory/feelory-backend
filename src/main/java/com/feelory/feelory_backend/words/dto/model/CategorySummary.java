package com.feelory.feelory_backend.words.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummary {
    private Long id;
    private String name;
    private String description;

    public static CategorySummary fromDto(Category dto) {

        return CategorySummary.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
