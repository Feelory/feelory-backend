package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.CustomException;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoryListResponse getCategories(CategoryListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WordCategories> wordCategories = categoriesRepository.searchCategoriesByIsActive(request.isActive(), pageable);

        return CategoryListResponse.fromPage(wordCategories);
    }

    public CategoryCreateResponse registerCategory(CategoryCreateRequest request) {

        boolean isExist = categoriesRepository.existsByName(request.getName());

        if(isExist) {
            throw new CustomException(ErrorCode.EXIST_CATEGORY_NAME);
        }

        WordCategories entity = categoriesRepository.save(request.toEntity());

        Category category = Category.fromEntity(entity);

        return CategoryCreateResponse.builder()
                .category(category)
                .build();
    }
}
