package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.dto.model.WritingGoalListSearch;
import org.springframework.data.domain.Page;


public interface WritingGoalsRepositoryCustom {

    Page<WritingGoals> searchWritingGoalsByDto(WritingGoalListSearch searchDto);
}
