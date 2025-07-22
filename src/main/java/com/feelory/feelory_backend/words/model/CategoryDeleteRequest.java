package com.feelory.feelory_backend.words.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class CategoryDeleteRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
}
