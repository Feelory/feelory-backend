package com.feelory.feelory_backend.domain.writing.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalSummaryDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public static WritingGoalSummaryDto fromDto(WritingGoalDto dto) {

        return WritingGoalSummaryDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
