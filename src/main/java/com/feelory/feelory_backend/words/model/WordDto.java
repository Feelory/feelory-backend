package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.Words;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private Long id;
    private CategoryDto category;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static WordDto fromEntity(Words words) {
        CategoryDto category = CategoryDto.fromEntity(words.getCategory());

        return WordDto.builder()
                .id(words.getId())
                .category(category)
                .name(words.getName())
                .description(words.getDescription())
                .createdAt(words.getCreatedAt())
                .updatedAt(words.getUpdatedAt())
                .isActive(words.getIsActive())
                .build();
    }
}
