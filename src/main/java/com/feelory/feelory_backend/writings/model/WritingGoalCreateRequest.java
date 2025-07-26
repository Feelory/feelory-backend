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
    private String startDate;
    private String endDate;

    public LocalDateTime getParsedStartDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(this.startDate, formatter).atStartOfDay();
        } catch(DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    public LocalDateTime getParsedEndDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(this.endDate, formatter).atStartOfDay();
        } catch(DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    public WritingGoals toEntity() {
        LocalDateTime parsedStartDate = getParsedStartDate();
        LocalDateTime parsedEndDate = getParsedEndDate();

        return WritingGoals.builder()
                .userId(this.userId)
                .name(this.name)
                .description(this.description)
                .startDate(parsedStartDate)
                .endDate(parsedEndDate)
                .build();
    }
}
