package com.feelory.feelory_backend.domain.writing.repository;

import com.feelory.feelory_backend.domain.word.entity.DailyWord;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyWordWritingRepository extends JpaRepository<DailyWordWriting, Long>, DailyWordWritingRepositoryCustom {

    Boolean existsByUserIdAndDailyWord(Long userId, DailyWord dailyWord);

    Boolean existsByUserIdAndWritingGoal(Long userId, WritingGoal writingGoal);

    @EntityGraph(attributePaths = {
            "user",
            "dailyWord",
            "writingGoal"
    })
    Optional<DailyWordWriting> findByIdAndUserIdAndIsActive(Long id, Long userId, Boolean isActive);
}
