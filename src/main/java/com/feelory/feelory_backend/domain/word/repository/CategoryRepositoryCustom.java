package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.WordCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<WordCategory> searchCategoriesByIsActive(Boolean isActive, Pageable pageable);
}
