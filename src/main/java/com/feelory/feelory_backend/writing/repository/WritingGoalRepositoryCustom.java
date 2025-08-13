package com.feelory.feelory_backend.writing.repository;

import com.feelory.feelory_backend.writing.entity.WritingGoal;
import com.feelory.feelory_backend.writing.dto.model.WritingGoalListSearchDto;
import org.springframework.data.domain.Page;


public interface WritingGoalRepositoryCustom {

    Page<WritingGoal> searchWritingGoalsByDto(WritingGoalListSearchDto searchDto);
}
