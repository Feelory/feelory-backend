package com.feelory.feelory_backend.writings.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalListRequest {
    private int page = 0 ;
    private int size = 10;
    private Boolean isValidDate = true;
    private Boolean isActive = true;
}
