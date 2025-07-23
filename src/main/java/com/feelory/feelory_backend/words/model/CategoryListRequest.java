package com.feelory.feelory_backend.words.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListRequest {
    private int page = 0 ;
    private int size = 10;
    private boolean isActive = true;
}
