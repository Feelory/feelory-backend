package com.feelory.feelory_backend.domain.word.dto.model;

import com.feelory.feelory_backend.domain.word.entity.WordCategory;
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

    public static CategoryDto fromEntity(WordCategory wordCategory){

        return CategoryDto.builder()
                .id(wordCategory.getId())
                .name(wordCategory.getName())
                .description(wordCategory.getDescription())
                .createdAt(wordCategory.getCreatedAt())
                .updatedAt(wordCategory.getUpdatedAt())
                .isActive(wordCategory.isActive())
                .build();
    }
}
