package com.feelory.feelory_backend.words.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
    private String name;
    private String description;
}
