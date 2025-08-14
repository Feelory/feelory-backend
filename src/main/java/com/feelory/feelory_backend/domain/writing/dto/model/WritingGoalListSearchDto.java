package com.feelory.feelory_backend.domain.writing.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalListSearchDto {

    private Long userId;
    private Pageable pageable;
    private Boolean isValidDate;
    private Boolean isActive;
}
