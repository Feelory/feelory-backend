package com.feelory.feelory_backend.word.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateCategoryNameException;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.word.dto.model.CategoryDto;
import com.feelory.feelory_backend.word.dto.request.CategoryCreateRequest;
import com.feelory.feelory_backend.word.dto.request.CategoryDeleteRequest;
import com.feelory.feelory_backend.word.dto.request.CategoryListRequest;
import com.feelory.feelory_backend.word.dto.request.CategoryUpdateRequest;
import com.feelory.feelory_backend.word.dto.response.CategoryCreateResponse;
import com.feelory.feelory_backend.word.dto.response.CategoryListResponse;
import com.feelory.feelory_backend.word.entity.WordCategory;
import com.feelory.feelory_backend.word.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoriesRepository;
    private final ValidationUtil validationUtil;

    public CategoryListResponse getCategories(CategoryListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WordCategory> wordCategories = categoriesRepository.searchCategoriesByIsActive(request.isActive(), pageable);

        return CategoryListResponse.fromPage(wordCategories);
    }

    @Transactional
    public CategoryCreateResponse registerCategory(CategoryCreateRequest request) {
        checkDuplicateName(request.getName());

        WordCategory entity = request.toEntity();
        WordCategory createdCategory = categoriesRepository.save(entity);


        CategoryDto category = CategoryDto.fromEntity(createdCategory);

        return CategoryCreateResponse.builder()
                .category(category)
                .build();
    }

    @Transactional
    public void modifyCategory(CategoryUpdateRequest request) {

        WordCategory entity = categoriesRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(CategoryNotFoundException::new);


        WordCategory.WordCategoryBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getName())) {
            checkDuplicateName(request.getName());
            builder.name(request.getName());
        }

        if (validationUtil.hasText(request.getDescription())) {
            builder.description(request.getDescription());
        }


        WordCategory updatedCategory = builder.build();
        categoriesRepository.save(updatedCategory);
    }

    @Transactional
    public void removeCategory(CategoryDeleteRequest request) {

        WordCategory entity = categoriesRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(CategoryNotFoundException::new);

        WordCategory updated = entity.toBuilder()
                .isActive(false)
                .build();

        categoriesRepository.save(updated);
    }

    private void checkDuplicateName(String name) {
        boolean isExist = categoriesRepository.existsByName(name);

        if(isExist) {
            throw new DuplicateCategoryNameException();
        }
    }
}
