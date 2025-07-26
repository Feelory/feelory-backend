package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.global.exception.exceptions.common.InvalidDateFormatException;
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
public class WritingGoalUpdateRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
    private String name;
    private String description;
    private Integer duration;

    public LocalDateTime getStartDate() {
        if (this.duration == null) return null;
        return LocalDate.now().atStartOfDay();
    }

    public LocalDateTime getEndDate() {
        if (duration == null) return null;

        return getStartDate()
                .plusDays(duration - 1L)
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }
}
