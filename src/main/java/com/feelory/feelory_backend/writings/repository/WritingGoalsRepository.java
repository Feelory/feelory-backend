package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.WritingGoals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingGoalsRepository extends JpaRepository<WritingGoals, Long>, WritingGoalsRepositoryCustom {

    Boolean existsByUserIdAndName(Long userId, String name);
}
