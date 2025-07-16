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
public class Categories {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Categories fromEntity(WordCategories wordCategories){

        return Categories.builder()
                .id(wordCategories.getId())
                .name(wordCategories.getName())
                .createdAt(wordCategories.getCreatedAt())
                .updatedAt(wordCategories.getUpdatedAt())
                .build();
    }
}
