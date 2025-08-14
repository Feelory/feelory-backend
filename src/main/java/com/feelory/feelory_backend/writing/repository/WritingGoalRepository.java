package com.feelory.feelory_backend.writing.repository;

import com.feelory.feelory_backend.writing.entity.WritingGoal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WritingGoalRepository extends JpaRepository<WritingGoal, Long>, WritingGoalRepositoryCustom {

    Boolean existsByUserIdAndName(Long userId, String name);

    @EntityGraph(attributePaths = "user")
    Optional<WritingGoal> findByIdAndUserIdAndIsActive(Long id, Long userId, Boolean isActive);

    @EntityGraph(attributePaths = "user")
    Optional<WritingGoal> findByIdAndUserId(Long id, Long userId);
}
