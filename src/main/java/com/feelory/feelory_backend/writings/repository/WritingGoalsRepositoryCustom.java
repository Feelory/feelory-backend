package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.WritingGoalListSearchDto;
import org.springframework.data.domain.Page;


public interface WritingGoalsRepositoryCustom {

    Page<WritingGoals> searchWritingGoalsByDto(WritingGoalListSearchDto searchDto);
}
