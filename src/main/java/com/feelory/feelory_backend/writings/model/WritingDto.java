package com.feelory.feelory_backend.writings.model;

import com.feelory.feelory_backend.words.dto.model.DailyWord;
import com.feelory.feelory_backend.words.dto.model.DailyWordSummary;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingDto {
    private Long id;
    private Long userId;
    private String content;
    private DailyWordSummary dailyWord;
    private WritingGoalSummaryDto writingGoal;
    private Boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private int likes;
    private int bookmarks;

    /*
        TODO. [TR-YOO] 북마크 / 좋아요 기능 구현 후 likes, bookmarks 수정하기
    */
    public static WritingDto fromEntity(DailyWordWritings writings) {
        DailyWord dailyWord = DailyWord.fromEntity(writings.getDailyWord());
        DailyWordSummary dailyWordSummary = DailyWordSummary.fromDto(dailyWord);
        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(writings.getWritingGoal());
        WritingGoalSummaryDto goalSummary = WritingGoalSummaryDto.fromDto(writingGoal);

        return WritingDto.builder()
                .id(writings.getId())
                .userId(writingGoal.getUserId())
                .content(writings.getContent())
                .dailyWord(dailyWordSummary)
                .writingGoal(goalSummary)
                .visibility(writings.getVisibility())
                .createdAt(writings.getCreatedAt())
                .updatedAt(writings.getUpdatedAt())
                .isActive(writings.getIsActive())
                .likes(0)
                .bookmarks(0)
                .build();
    }
}
