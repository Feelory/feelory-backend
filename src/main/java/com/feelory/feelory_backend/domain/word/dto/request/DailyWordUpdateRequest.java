package com.feelory.feelory_backend.domain.word.dto.request;

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
public class DailyWordUpdateRequest {
    @NotNull(message = "수정할 오늘의 단어 ID는 필수입니다.")
    private Long id;
    private Long wordId;
    private String topicDate;
    private String description;

    public LocalDateTime getParsedTopicDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(this.topicDate, formatter).atStartOfDay();
        } catch(DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }
}
