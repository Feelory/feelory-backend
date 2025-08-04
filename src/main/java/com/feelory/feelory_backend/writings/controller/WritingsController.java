package com.feelory.feelory_backend.writings.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.writings.model.*;
import com.feelory.feelory_backend.writings.service.WritingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/writings")
@RequiredArgsConstructor
@Tag(name = "글쓰기 (Writings)", description = "글쓰기 API 목록")
public class WritingsController {

    private final WritingsService writingsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "내가 쓴 글 목록",
            description = "내가 쓴 글 목록 조회 API"
    )
    @GetMapping("/me")
    public ApiResponse<UserWritingListResponse> getUserWritings(UserWritingListRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingListResponse response = writingsService.getUserWritings(request);

        return ApiResponse.success(response, SuccessCode.GET_USER_WRITINGS_LIST_SUCCESS);
    }

    @Operation(
            summary = "오늘 내가 쓴 글 조회",
            description = "오늘 내가 쓴 글 조회 API"
    )
    @GetMapping("/me/today")
    public ApiResponse<UserTodayWritingResponse> getUserTodayWriting() {
        jwtTokenProvider.checkUserOrAdmin();

        UserTodayWritingResponse response = writingsService.getUserTodayWriting();

        return ApiResponse.success(response, SuccessCode.GET_USER_TODAY_WRITING_SUCCESS);
    }

    @Operation(
            summary = "내가 쓴 글 상세 조회",
            description = "내가 쓴 글 상세 조회 API"
    )
    @GetMapping("/me/{id}")
    public ApiResponse<UserWritingDetailResponse> getUserWritingDetail(@PathVariable Long id) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingDetailResponse response = writingsService.getUserWritingDetail(id);

        return ApiResponse.success(response, SuccessCode.GET_USER_TODAY_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 작성",
            description = "글 작성 API"
    )
    @PostMapping("")
    public ApiResponse<UserWritingCreateResponse> postUserWriting(@Valid @RequestBody UserWritingCreateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingCreateResponse response = writingsService.registerUserWriting(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WRITING_SUCCESS);
    }

    @Operation(
            summary = "글 수정",
            description = "글 수정 API"
    )
    @PatchMapping("")
    public ApiResponse<UserWritingUpdateResponse> patchUserWriting(@Valid @RequestBody UserWritingUpdateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingUpdateResponse response = writingsService.modifyUserWriting(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_WRITING_SUCCESS);
    }


    @DeleteMapping("")
    public ApiResponse<UserWritingDeleteResponse> deleteUserWriting(@Valid UserWritingDeleteRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        UserWritingDeleteResponse response = writingsService.removeUserWriting(request);

        return ApiResponse.success(response, SuccessCode.DELETE_WRITING_SUCCESS);
    }

    @PatchMapping("/visibility")
    public ApiResponse<VisibilityUpdateResponse> patchWritingVisibility(@Valid @RequestBody VisibilityUpdateRequest request) {
        jwtTokenProvider.checkUserOrAdmin();

        VisibilityUpdateResponse response = writingsService.modifyVisibility(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_WRITING_VISIBILITY_SUCCESS);
    }
}
