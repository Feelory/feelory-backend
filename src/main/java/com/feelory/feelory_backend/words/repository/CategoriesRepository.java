package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.WordCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<WordCategories, Long>, CategoriesRepositoryCustom {

    Boolean existsByName(String name);

    Optional<WordCategories> findByIdAndActive(Long id, Boolean isActive);
}
