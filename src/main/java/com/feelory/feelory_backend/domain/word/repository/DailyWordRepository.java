package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.DailyWord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyWordRepository extends JpaRepository<DailyWord, Long>, DailyWordRepositoryCustom {

    @EntityGraph(attributePaths = {
            "word",
            "word.category"
    })
    Optional<DailyWord> findByIdAndIsActive(Long id, Boolean isActive);

    boolean existsByWordId(Long wordId);
}
