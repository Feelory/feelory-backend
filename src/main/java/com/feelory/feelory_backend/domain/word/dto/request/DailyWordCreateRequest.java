package com.feelory.feelory_backend.domain.word.dto.request;

import com.feelory.feelory_backend.global.exception.exceptions.common.InvalidDateFormatException;
import com.feelory.feelory_backend.domain.word.entity.DailyWord;
import com.feelory.feelory_backend.domain.word.entity.Word;
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
public class DailyWordCreateRequest {
    @NotNull(message = "선정할 단어 ID는 필수입니다.")
    private Long wordId;
    @NotNull(message = "날짜 지정은 필수입니다.")
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

    public DailyWord toEntity(Word word) {
        LocalDateTime dateTime = getParsedTopicDate();

        return DailyWord.builder()
                .word(word)
                .topicDate(dateTime)
                .isActive(true)
                .build();
    }
}
