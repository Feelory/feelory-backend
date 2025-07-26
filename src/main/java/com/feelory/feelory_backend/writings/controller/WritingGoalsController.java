package com.feelory.feelory_backend.writings.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.writings.model.WritingGoalDetailResponse;
import com.feelory.feelory_backend.writings.model.WritingGoalListRequest;
import com.feelory.feelory_backend.writings.model.WritingGoalListResponse;
import com.feelory.feelory_backend.writings.service.WritingGoalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/writing-goals")
@RequiredArgsConstructor
@Tag(name = "글쓰기 목표 (WritingGoals)", description = "글쓰기 목표 API 목록")
public class WritingGoalsController {

    private final WritingGoalsService writingGoalsService;

    /*
        TODO. [TR YOO] 로그인 유저 검증 로직 필요
    */
    @Operation(
            summary = "글쓰기 목표 목록 조회",
            description = "글쓰기 목표 목록 조회 API"
    )
    @GetMapping("")
    public ApiResponse<WritingGoalListResponse> getWritingGoals(WritingGoalListRequest request) {

        WritingGoalListResponse response = writingGoalsService.getWritingGoals(request);

        return ApiResponse.success(response, SuccessCode.GET_WRITING_GOAL_LIST_SUCCESS);
    }

    /*
        TODO. [TR YOO] 로그인 유저 검증 로직 필요
    */
    @Operation(
            summary = "글쓰기 목표 상세 조회",
            description = "글쓰기 목표 상세 조회 API"
    )
    @GetMapping("/{id}")
    public ApiResponse<WritingGoalDetailResponse> getWritingGoalDetail(@PathVariable Long id) {

        WritingGoalDetailResponse response = writingGoalsService.getWritingGoalDetail(id);

        return ApiResponse.success(response, SuccessCode.GET_WRITING_GOAL_DETAIL_SUCCESS);
    }
}
