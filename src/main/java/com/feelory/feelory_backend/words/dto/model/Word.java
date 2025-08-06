package com.feelory.feelory_backend.words.dto.model;

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
public class Word {
    private Long id;
    private CategorySummary category;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static Word fromEntity(Words words) {
        Category category = Category.fromEntity(words.getCategory());
        CategorySummary summaryDto = CategorySummary.fromDto(category);

        return Word.builder()
                .id(words.getId())
                .category(summaryDto)
                .name(words.getName())
                .description(words.getDescription())
                .createdAt(words.getCreatedAt())
                .updatedAt(words.getUpdatedAt())
                .isActive(words.getIsActive())
                .build();
    }
}
