package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.global.exception.exceptions.common.InvalidDateFormatException;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingGoalCreateRequest {
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
    @NotBlank(message = "단어 이름은 필수입니다.")
    private String name;
    private String description;
    @NotNull(message = "기간은 필수입니다.")
    private Integer duration;

    public WritingGoals toEntity() {
        LocalDateTime startDate = LocalDate.now().atStartOfDay();

        LocalDateTime endDate = startDate
                .plusDays(this.duration - 1)
                .withHour(23).withMinute(59).withSecond(59);

        return WritingGoals.builder()
                .userId(this.userId)
                .name(this.name)
                .description(this.description)
                .duration(this.duration)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
