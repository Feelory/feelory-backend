package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.WordCategories;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
    @NotBlank(message = "카테고리 이름은 필수입니다.")
    private String name;
    private String description;

    public WordCategories toEntity() {
        return WordCategories.builder()
                .name(this.name)
                .description(this.description)
                .isActive(true)
                .build();
    }
}
