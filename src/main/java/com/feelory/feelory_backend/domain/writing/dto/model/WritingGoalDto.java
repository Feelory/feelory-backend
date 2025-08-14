package com.feelory.feelory_backend.domain.writing.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalDto {

    private Long id;
    private Long userId;
    private String name;
    private int duration;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public static WritingGoalDto fromEntity(WritingGoal writingGoal) {

        return WritingGoalDto.builder()
                .id(writingGoal.getId())
                .userId(writingGoal.getUser().getId())
                .name(writingGoal.getName())
                .duration(writingGoal.getDuration())
                .description(writingGoal.getDescription())
                .startDate(writingGoal.getStartDate().toLocalDate())
                .endDate(writingGoal.getEndDate().toLocalDate())
                .createdAt(writingGoal.getCreatedAt())
                .updatedAt(writingGoal.getUpdatedAt())
                .isActive(writingGoal.getIsActive())
                .build();
    }
}
