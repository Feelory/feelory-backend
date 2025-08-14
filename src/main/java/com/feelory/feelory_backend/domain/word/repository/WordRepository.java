package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.Word;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long>, WordRepositoryCustom {

    Boolean existsByName(String name);

    @EntityGraph(attributePaths = "category")
    Optional<Word> findByIdAndIsActive(Long id, Boolean isActive);
}
