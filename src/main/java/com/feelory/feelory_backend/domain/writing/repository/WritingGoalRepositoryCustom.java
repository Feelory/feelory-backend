package com.feelory.feelory_backend.domain.writing.repository;

import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingGoalListSearchDto;
import org.springframework.data.domain.Page;


public interface WritingGoalRepositoryCustom {

    Page<WritingGoal> searchWritingGoalsByDto(WritingGoalListSearchDto searchDto);
}
