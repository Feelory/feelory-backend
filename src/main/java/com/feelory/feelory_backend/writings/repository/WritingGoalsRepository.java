package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.WritingGoals;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WritingGoalsRepository extends JpaRepository<WritingGoals, Long>, WritingGoalsRepositoryCustom {

    Boolean existsByUserIdAndName(Long userId, String name);

    @EntityGraph(attributePaths = "user")
    Optional<WritingGoals> findByIdAndUserIdAndIsActive(Long id, Long userId, Boolean isActive);

    @EntityGraph(attributePaths = "user")
    Optional<WritingGoals> findByIdAndUserId(Long id, Long userId);
}
