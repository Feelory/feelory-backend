package com.feelory.feelory_backend.writings.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingUpdateRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
    private String title;
    private String content;
    private Long dailyWordId;
    private Long writingGoalId;
    private Boolean visibility;
}
