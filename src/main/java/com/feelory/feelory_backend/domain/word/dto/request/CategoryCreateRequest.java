package com.feelory.feelory_backend.domain.word.dto.request;

import com.feelory.feelory_backend.domain.word.entity.WordCategory;
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

    public WordCategory toEntity() {
        return WordCategory.builder()
                .name(this.name)
                .description(this.description)
                .isActive(true)
                .build();
    }
}
