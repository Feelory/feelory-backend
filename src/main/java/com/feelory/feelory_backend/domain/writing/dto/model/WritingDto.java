package com.feelory.feelory_backend.domain.writing.dto.model;

import com.feelory.feelory_backend.domain.word.dto.model.DailyWordDto;
import com.feelory.feelory_backend.domain.word.dto.model.DailyWordSummaryDto;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
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
    private DailyWordSummaryDto dailyWord;
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
    public static WritingDto fromEntity(DailyWordWriting writings) {
        DailyWordDto dailyWord = DailyWordDto.fromEntity(writings.getDailyWord());
        DailyWordSummaryDto dailyWordSummary = DailyWordSummaryDto.fromDto(dailyWord);
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
