package com.feelory.feelory_backend.writings.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.writings.model.UserTodayWritingRequest;
import com.feelory.feelory_backend.writings.model.UserTodayWritingResponse;
import com.feelory.feelory_backend.writings.model.UserWritingListRequest;
import com.feelory.feelory_backend.writings.model.UserWritingListResponse;
import com.feelory.feelory_backend.writings.service.WritingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/writings")
@RequiredArgsConstructor
@Tag(name = "글쓰기 (Writings)", description = "글쓰기 API 목록")
public class WritingsController {

    private final WritingsService writingsService;

    @Operation(
            summary = "내가 쓴 글 목록",
            description = "내가 쓴 글 목록 조회 API"
    )
    @GetMapping("/me")
    public ApiResponse<UserWritingListResponse> getUserWritings(UserWritingListRequest request) {

        UserWritingListResponse response = writingsService.getUserWritings(request);

        return ApiResponse.success(response, SuccessCode.GET_USER_WRITINGS_LIST_SUCCESS);
    }

    @Operation(
            summary = "오늘 내가 쓴 글 조회",
            description = "조건에 따른 모든 단어 카테고리 목록 조회 API"
    )
    @GetMapping("/me/today")
    public ApiResponse<UserTodayWritingResponse> getUserTodayWriting(UserTodayWritingRequest request) {

        UserTodayWritingResponse response = writingsService.getUserTodayWriting(request);

        return ApiResponse.success(response, SuccessCode.GET_USER_TODAY_WRITING_SUCCESS);
    }
}
