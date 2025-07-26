package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.WritingGoalSearchDto;

import java.util.Optional;

public interface WritingGoalsRepositoryCustom {

    Optional<WritingGoals> searchWritingGoalDetailByDto(WritingGoalSearchDto writingGoalSearchDto);
}
