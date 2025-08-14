package com.feelory.feelory_backend.word.dto.response;

import com.feelory.feelory_backend.word.dto.model.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateResponse {
    private CategoryDto category;
}
