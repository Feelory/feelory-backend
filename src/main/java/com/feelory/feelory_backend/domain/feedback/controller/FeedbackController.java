package com.feelory.feelory_backend.domain.feedback.controller;

import com.feelory.feelory_backend.domain.feedback.model.request.FeedbackRequest;
import com.feelory.feelory_backend.domain.feedback.model.response.FeedbackResponse;
import com.feelory.feelory_backend.domain.feedback.service.FeedbackService;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Tag(name = "글 피드백 (Feedback)", description = "유저 글 피드백 API 목록")
public class FeedbackController {

    private final JwtTokenProvider jwtTokenProvider;
    private final FeedbackService feedbackService;

    @Operation(
            summary = "AI 피드백 요청",
            description = "작성된 글의 id를 받아 LLM API에게 피드백 내용을 받아오는 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping(value = "")
    public ApiResponse<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest request) {

        jwtTokenProvider.checkUserOrAdmin();

        FeedbackResponse response = feedbackService.createFeedback(request);

        return ApiResponse.success(response, SuccessCode.DEFAULT_SUCCESS);
    }
}
