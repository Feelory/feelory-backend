package com.feelory.feelory_backend.feedbacks.controller;

import com.feelory.feelory_backend.feedbacks.model.request.FeedbackRequest;
import com.feelory.feelory_backend.feedbacks.model.response.FeedbackResponse;
import com.feelory.feelory_backend.feedbacks.service.FeedbackService;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.users.model.response.UserProfileImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Tag(name = "글 피드백 (Feedback)", description = "유저 글 피드백 API 목록")
public class FeedbackController {

    private final JwtTokenProvider jwtTokenProvider;
    private final FeedbackService feedbackService;

    @Operation(
            summary = "유저 프로필 이미지 갱신(생성/교체)",
            description = "기존 이미지가 없으면 생성, 있으면 교체",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping(value = "")
    public ApiResponse<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest request) {

        jwtTokenProvider.checkUserOrAdmin();

        FeedbackResponse response = feedbackService.createFeedback(request);

        return ApiResponse.success(response, SuccessCode.DEFAULT_SUCCESS);
    }
}
