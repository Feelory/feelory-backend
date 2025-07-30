package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyWordWritingsRepository extends JpaRepository<DailyWordWritings, Long>, DailyWordWritingsRepositoryCustom {

    Boolean existsByUserIdAndDailyWord(Long userId, DailyWords dailyWord);

    Boolean existsByUserIdAndWritingGoal(Long userId, WritingGoals writingGoal);

    @EntityGraph(attributePaths = {
            "dailyWord",
            "writingGoal"
    })
    Optional<DailyWordWritings> findByIdAndIsActive(Long id, Boolean isActive);
}
