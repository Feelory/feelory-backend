package com.feelory.feelory_backend.writing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritingGoalDeleteRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
}
