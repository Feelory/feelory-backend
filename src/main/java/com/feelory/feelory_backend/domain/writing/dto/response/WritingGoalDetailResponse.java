package com.feelory.feelory_backend.domain.writing.dto.response;

import com.feelory.feelory_backend.domain.writing.dto.model.WritingGoalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalDetailResponse {
    private WritingGoalDto writingGoal;
}
