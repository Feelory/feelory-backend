package com.feelory.feelory_backend.writings.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.exception.exceptions.words.InvalidDateFormatException;
import com.feelory.feelory_backend.writings.model.WritingGoalDetailResponse;
import com.feelory.feelory_backend.writings.service.WritingGoalsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/writing-goals")
@RequiredArgsConstructor
@Tag(name = "글쓰기 목표 (WritingGoals)", description = "글쓰기 목표 API 목록")
public class WritingGoalsController {

    private final WritingGoalsService writingGoalsService;

    /*
        TODO. [TR YOO] 로그인 API 구현 후 userId 파라미터 제거하기
    */
    @GetMapping("/{date}")
    public ApiResponse<WritingGoalDetailResponse> getWritingGoalDetail(@PathVariable String date, Long userId) {

        LocalDateTime parsed;

        try {
            parsed = LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        WritingGoalDetailResponse response = writingGoalsService.getWritingGoalDetail(parsed, userId);

        return ApiResponse.success(response, SuccessCode.GET_WRITING_GOAL_DETAIL_SUCCESS);
    }
}
