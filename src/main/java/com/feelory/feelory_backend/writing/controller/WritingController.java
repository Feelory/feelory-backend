package com.feelory.feelory_backend.writing.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.writing.dto.request.*;
import com.feelory.feelory_backend.writing.dto.response.*;
import com.feelory.feelory_backend.writing.service.WritingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/writings")
@RequiredArgsConstructor
@Tag(name = "글쓰기 (Writings)", description = "글쓰기 API 목록")
public class WritingController {

    private final WritingService writingService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "내가 쓴 글 목록",
            description = "내가 쓴 글 목록 조회 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping("/me")
    public ApiResponse<UserWritingListResponse> getUserWritings(@ParameterObject UserWritingListRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingListResponse response = writingService.getUserWritings(request);

        return ApiResponse.success(response, SuccessCode.GET_USER_WRITINGS_LIST_SUCCESS);
    }

    @Operation(
            summary = "오늘 내가 쓴 글 조회",
            description = "오늘 내가 쓴 글 조회 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping("/me/today")
    public ApiResponse<UserTodayWritingResponse> getUserTodayWriting() {
        jwtTokenProvider.checkUserOrAdmin();

        UserTodayWritingResponse response = writingService.getUserTodayWriting();

        return ApiResponse.success(response, SuccessCode.GET_USER_TODAY_WRITING_SUCCESS);
    }

    @Operation(
            summary = "내가 쓴 글 상세 조회",
            description = "내가 쓴 글 상세 조회 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping("/me/{id}")
    public ApiResponse<UserWritingDetailResponse> getUserWritingDetail(@PathVariable Long id) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingDetailResponse response = writingService.getUserWritingDetail(id);

        return ApiResponse.success(response, SuccessCode.GET_USER_TODAY_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 작성",
            description = "글 작성 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("")
    public ApiResponse<UserWritingCreateResponse> postUserWriting(@Valid @RequestBody UserWritingCreateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingCreateResponse response = writingService.registerUserWriting(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 수정",
            description = "글 수정 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PatchMapping("")
    public ApiResponse<Void> patchUserWriting(@Valid @RequestBody UserWritingUpdateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        writingService.modifyUserWriting(request);

        return ApiResponse.success(SuccessCode.UPDATE_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 삭제",
            description = "글 삭제 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @DeleteMapping("")
    public ApiResponse<Void> deleteUserWriting(@Valid UserWritingDeleteRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        writingService.removeUserWriting(request);

        return ApiResponse.success(SuccessCode.DELETE_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 공개 여부 변경",
            description = "글 공개 여부 변경 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PatchMapping("/visibility")
    public ApiResponse<Void> patchWritingVisibility(@Valid @RequestBody VisibilityUpdateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        writingService.modifyVisibility(request);

        return ApiResponse.success(SuccessCode.UPDATE_WRITING_VISIBILITY_SUCCESS);
    }
}
