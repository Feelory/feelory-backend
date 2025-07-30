package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
public class UserWritingDeleteRequest {
    @NotNull(message = "id 값은 필수입니다.")
    private Long id;
}
