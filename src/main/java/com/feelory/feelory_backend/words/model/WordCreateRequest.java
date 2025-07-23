package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.entity.Words;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordCreateRequest {
    @NotBlank(message = "단어 이름은 필수입니다.")
    private String name;
    private String description;
    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    public Words toEntity(WordCategories category) {

        return Words.builder()
                .name(this.name)
                .description(this.description)
                .category(category)
                .isActive(true)
                .build();
    }
}
