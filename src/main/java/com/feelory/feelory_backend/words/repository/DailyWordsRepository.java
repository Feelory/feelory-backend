package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.DailyWords;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyWordsRepository extends JpaRepository<DailyWords, Long>, DailyWordsRepositoryCustom {

    @EntityGraph(attributePaths = {
            "word",
            "word.category"
    })
    Optional<DailyWords> findByIdAndIsActive(Long id, Boolean isActive);
}
