package com.feelory.feelory_backend.domain.writing.dto.request;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class WritingGoalCreateRequest {
    @NotBlank(message = "단어 이름은 필수입니다.")
    private String name;
    private String description;
    @NotNull(message = "기간은 필수입니다.")
    private Integer duration;

    public WritingGoal toEntity(User user) {
        LocalDateTime startDate = LocalDate.now().atStartOfDay();

        LocalDateTime endDate = startDate
                .plusDays(this.duration - 1)
                .withHour(23).withMinute(59).withSecond(59);

        return WritingGoal.builder()
                .name(this.name)
                .user(user)
                .description(this.description)
                .duration(this.duration)
                .startDate(startDate)
                .endDate(endDate)
                .isActive(true)
                .build();
    }
}
