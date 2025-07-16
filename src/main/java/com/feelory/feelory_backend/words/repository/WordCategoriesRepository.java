package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.WordCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordCategoriesRepository extends JpaRepository<WordCategories, Long> {
}
