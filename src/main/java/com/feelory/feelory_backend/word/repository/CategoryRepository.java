package com.feelory.feelory_backend.word.repository;

import com.feelory.feelory_backend.word.entity.WordCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<WordCategory, Long>, CategoryRepositoryCustom {

    Boolean existsByName(String name);

    Optional<WordCategory> findByIdAndIsActive(Long id, Boolean isActive);
}
