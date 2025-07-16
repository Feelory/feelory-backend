package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.WordCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriesRepositoryCustom {

    Page<WordCategories> searchCategoriesByIsActive(Boolean isActive, Pageable pageable);
}
