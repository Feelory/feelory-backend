package com.feelory.feelory_backend.words.dto.response;

import com.feelory.feelory_backend.words.dto.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDeleteResponse {
    private Category category;
}
