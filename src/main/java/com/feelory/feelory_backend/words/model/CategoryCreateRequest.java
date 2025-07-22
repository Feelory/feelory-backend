package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.WordCategories;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
    @NotNull
    private String name;
    private String description;

    public WordCategories toEntity() {
        return WordCategories.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
