package com.feelory.feelory_backend.writing.dto.request;

import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.word.entity.DailyWord;
import com.feelory.feelory_backend.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.writing.entity.WritingGoal;
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
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    @NotNull(message = "오늘의 단어 ID는 필수입니다.")
    private Long dailyWordId;
    @NotNull(message = "글쓰기 목표 ID는 필수입니다.")
    private Long writingGoalId;
    private Boolean visibility = true;

    public DailyWordWriting toEntity(Users user, DailyWord dailyWord, WritingGoal writingGoal) {

        return DailyWordWriting.builder()
                .user(user)
                .dailyWord(dailyWord)
                .writingGoal(writingGoal)
                .content(this.content)
                .visibility(this.visibility)
                .isActive(true)
                .build();
    }
}
