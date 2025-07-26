package com.feelory.feelory_backend.writings.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
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

    public static WritingGoalDto fromEntity(WritingGoals writingGoals) {

        return WritingGoalDto.builder()
                .id(writingGoals.getId())
                .userId(writingGoals.getUserId())
                .name(writingGoals.getName())
                .duration(writingGoals.getDuration())
                .description(writingGoals.getDescription())
                .startDate(writingGoals.getStartDate().toLocalDate())
                .endDate(writingGoals.getEndDate().toLocalDate())
                .createdAt(writingGoals.getCreatedAt())
                .updatedAt(writingGoals.getUpdatedAt())
                .isActive(writingGoals.getIsActive())
                .build();
    }
}
