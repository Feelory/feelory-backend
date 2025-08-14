package com.feelory.feelory_backend.domain.word.dto.model;

import com.feelory.feelory_backend.domain.word.entity.Word;
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
    private CategorySummaryDto category;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static WordDto fromEntity(Word word) {
        CategoryDto category = CategoryDto.fromEntity(word.getCategory());
        CategorySummaryDto summaryDto = CategorySummaryDto.fromDto(category);

        return WordDto.builder()
                .id(word.getId())
                .category(summaryDto)
                .name(word.getName())
                .description(word.getDescription())
                .createdAt(word.getCreatedAt())
                .updatedAt(word.getUpdatedAt())
                .isActive(word.getIsActive())
                .build();
    }
}
