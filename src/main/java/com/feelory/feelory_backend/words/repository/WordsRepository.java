package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.Words;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordsRepository extends JpaRepository<Words, Long>, WordsRepositoryCustom {

    Boolean existsByName(String name);

    @EntityGraph(attributePaths = "category")
    Optional<Words> findByIdAndIsActive(Long id, Boolean isActive);
}
