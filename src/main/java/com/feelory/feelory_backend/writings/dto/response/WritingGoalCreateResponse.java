package com.feelory.feelory_backend.writings.dto.response;

import com.feelory.feelory_backend.writings.dto.model.WritingGoalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalCreateResponse {
    private WritingGoalDto writingGoal;
}
