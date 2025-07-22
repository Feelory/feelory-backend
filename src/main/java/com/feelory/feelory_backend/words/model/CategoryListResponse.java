package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.WordCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponse {

    private Long total;
    private int page;
    private int size;
    private boolean hasNext;
    private List<Category> categories;

    public static CategoryListResponse fromPage(Page<WordCategories> page) {
        List<Category> categoryList = page.getContent().stream()
                .map(Category::fromEntity)
                .toList();

        return CategoryListResponse.builder()
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .categories(categoryList)
                .build();
    }
}
