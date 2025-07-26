package com.feelory.feelory_backend.writings.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.writings.model.*;
import com.feelory.feelory_backend.writings.service.WritingGoalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

    /*
        TODO. [TR YOO] 로그인 유저 검증 로직 필요
    */
    @Operation(
            summary = "글쓰기 목표 추가",
            description = "글쓰기 목표 추가 API"
    )
    @PostMapping("")
    public ApiResponse<WritingGoalCreateResponse> postWritingGoal(@Valid @RequestBody WritingGoalCreateRequest request) {

        WritingGoalCreateResponse response = writingGoalsService.registerWritingGoal(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WRITING_GOAL_SUCCESS);
    }
}
