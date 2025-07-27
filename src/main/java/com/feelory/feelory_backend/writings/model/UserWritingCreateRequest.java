package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    TODO. [TR-YOO] 로그인 기능 완성 후 userId 필드 제거하기
*/
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingCreateRequest {
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    @NotNull(message = "오늘의 단어 ID는 필수입니다.")
    private Long dailyWordId;
    @NotNull(message = "글쓰기 목표 ID는 필수입니다.")
    private Long writingGoalId;
    private Boolean visibility = true;

    public DailyWordWritings toEntity(DailyWords dailyWord, WritingGoals writingGoal) {

        return DailyWordWritings.builder()
                .dailyWord(dailyWord)
                .userId(this.userId)
                .writingGoal(writingGoal)
                .title(this.title)
                .content(this.content)
                .visibility(this.visibility)
                .isActive(true)
                .build();
    }
}
