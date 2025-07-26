package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.WordCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static CategoryDto fromEntity(WordCategories wordCategories){

        return CategoryDto.builder()
                .id(wordCategories.getId())
                .name(wordCategories.getName())
                .description(wordCategories.getDescription())
                .createdAt(wordCategories.getCreatedAt())
                .updatedAt(wordCategories.getUpdatedAt())
                .isActive(wordCategories.isActive())
                .build();
    }
}
