package com.feelory.feelory_backend.writing.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.writing.dto.request.WritingGoalCreateRequest;
import com.feelory.feelory_backend.writing.dto.request.WritingGoalDeleteRequest;
import com.feelory.feelory_backend.writing.dto.request.WritingGoalListRequest;
import com.feelory.feelory_backend.writing.dto.request.WritingGoalUpdateRequest;
import com.feelory.feelory_backend.writing.dto.response.*;
import com.feelory.feelory_backend.writing.service.WritingGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/writing-goals")
@RequiredArgsConstructor
@Tag(name = "글쓰기 목표 (WritingGoals)", description = "글쓰기 목표 API 목록")
public class WritingGoalController {

    private final WritingGoalService writingGoalService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "글쓰기 목표 목록 조회",
            description = "글쓰기 목표 목록 조회 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping("")
    public ApiResponse<WritingGoalListResponse> getWritingGoals(@ParameterObject WritingGoalListRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        WritingGoalListResponse response = writingGoalService.getWritingGoals(request);

        return ApiResponse.success(response, SuccessCode.GET_WRITING_GOAL_LIST_SUCCESS);
    }

    @Operation(
            summary = "글쓰기 목표 상세 조회",
            description = "글쓰기 목표 상세 조회 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping("/{id}")
    public ApiResponse<WritingGoalDetailResponse> getWritingGoalDetail(@PathVariable Long id) {
        jwtTokenProvider.checkUserOrAdmin();

        WritingGoalDetailResponse response = writingGoalService.getWritingGoalDetail(id);

        return ApiResponse.success(response, SuccessCode.GET_WRITING_GOAL_DETAIL_SUCCESS);
    }

    @Operation(
            summary = "글쓰기 목표 추가",
            description = "글쓰기 목표 추가 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("")
    public ApiResponse<WritingGoalCreateResponse> postWritingGoal(@Valid @RequestBody WritingGoalCreateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        WritingGoalCreateResponse response = writingGoalService.registerWritingGoal(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WRITING_GOAL_SUCCESS);
    }

    @Operation(
            summary = "글쓰기 목표 수정",
            description = "글쓰기 목표 수정 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PatchMapping("")
    public ApiResponse<Void> patchWritingGoal(@Valid @RequestBody WritingGoalUpdateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        writingGoalService.modifyWritingGoal(request);

        return ApiResponse.success(SuccessCode.UPDATE_WRITING_GOAL_SUCCESS);
    }


    @Operation(
            summary = "글쓰기 목표 삭제",
            description = "글쓰기 목표 삭제 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @DeleteMapping("")
    public ApiResponse<Void> deleteWritingGoal(@Valid WritingGoalDeleteRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        writingGoalService.removeWritingGoal(request);

        return ApiResponse.success(SuccessCode.DELETE_WRITING_GOAL_SUCCESS);
    }
}
