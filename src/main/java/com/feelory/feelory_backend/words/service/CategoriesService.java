package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateCategoryNameException;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.words.dto.model.Category;
import com.feelory.feelory_backend.words.dto.request.CategoryCreateRequest;
import com.feelory.feelory_backend.words.dto.request.CategoryDeleteRequest;
import com.feelory.feelory_backend.words.dto.request.CategoryListRequest;
import com.feelory.feelory_backend.words.dto.request.CategoryUpdateRequest;
import com.feelory.feelory_backend.words.dto.response.CategoryCreateResponse;
import com.feelory.feelory_backend.words.dto.response.CategoryDeleteResponse;
import com.feelory.feelory_backend.words.dto.response.CategoryListResponse;
import com.feelory.feelory_backend.words.dto.response.CategoryUpdateResponse;
import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final ValidationUtil validationUtil;

    public CategoryListResponse getCategories(CategoryListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WordCategories> wordCategories = categoriesRepository.searchCategoriesByIsActive(request.isActive(), pageable);

        return CategoryListResponse.fromPage(wordCategories);
    }

    @Transactional
    public CategoryCreateResponse registerCategory(CategoryCreateRequest request) {
        checkDuplicateName(request.getName());

        WordCategories entity = request.toEntity();
        WordCategories createdCategory = categoriesRepository.save(entity);


        Category category = Category.fromEntity(createdCategory);

        return CategoryCreateResponse.builder()
                .category(category)
                .build();
    }

    @Transactional
    public CategoryUpdateResponse modifyCategory(CategoryUpdateRequest request) {

        WordCategories entity = categoriesRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(CategoryNotFoundException::new);


        WordCategories.WordCategoriesBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getName())) {
            checkDuplicateName(request.getName());
            builder.name(request.getName());
        }

        if (validationUtil.hasText(request.getDescription())) {
            builder.description(request.getDescription());
        }


        WordCategories updatedCategory = builder.build();
        WordCategories updatedEntity = categoriesRepository.save(updatedCategory);


        Category category = Category.fromEntity(updatedEntity);

        return CategoryUpdateResponse.builder()
                .category(category)
                .build();
    }

    @Transactional
    public CategoryDeleteResponse removeCategory(CategoryDeleteRequest request) {

        WordCategories entity = categoriesRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(CategoryNotFoundException::new);

        WordCategories updated = entity.toBuilder()
                .isActive(false)
                .build();

        categoriesRepository.save(updated);

        WordCategories loaded = categoriesRepository.findByIdAndIsActive(updated.getId(), false)
                .orElseThrow(CategoryNotFoundException::new);

        Category category = Category.fromEntity(loaded);

        return CategoryDeleteResponse.builder()
                .category(category)
                .build();
    }

    private void checkDuplicateName(String name) {
        boolean isExist = categoriesRepository.existsByName(name);

        if(isExist) {
            throw new DuplicateCategoryNameException();
        }
    }
}
