package com.feelory.feelory_backend.writings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalListRequest {
    private int page = 0 ;
    private int size = 10;
    private Long userId;
    private Boolean isValidDate = true;
    private Boolean isActive = true;
}
