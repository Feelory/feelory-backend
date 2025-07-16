package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.model.CategoriesRequest;
import com.feelory.feelory_backend.words.model.CategoriesResponse;
import com.feelory.feelory_backend.words.repository.CategoriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {

    CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public CategoriesResponse getCategories(CategoriesRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WordCategories> wordCategories = categoriesRepository.searchCategoriesByIsActive(request.isActive(), pageable);

        return CategoriesResponse.fromPage(wordCategories);
    }
}
