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
public class Word {
    private Long id;
    private Category category;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static Word fromEntity(Words words) {
        Category category = Category.fromEntity(words.getCategory());

        return Word.builder()
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
