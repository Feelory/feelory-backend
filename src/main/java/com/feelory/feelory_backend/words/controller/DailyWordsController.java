package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.words.docs.DailyWordsDocs;
import com.feelory.feelory_backend.words.model.DailyWordCreateRequest;
import com.feelory.feelory_backend.words.model.DailyWordCreateResponse;
import com.feelory.feelory_backend.words.service.DailyWordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/daily-words")
@RequiredArgsConstructor
@Tag(name = "오늘의 단어 (DailyWords)", description = "오늘의 단어 API 목록")
public class DailyWordsController {

    private final DailyWordsService dailyWordsService;

    @Operation(
            summary = "오늘의 단어 추가",
            description = DailyWordsDocs.POST_DAILY_WORD_DESCRIPTION
    )
    @PostMapping("")
    public ApiResponse<DailyWordCreateResponse> postDailyWord(@Valid @RequestBody DailyWordCreateRequest request) {

        DailyWordCreateResponse response = dailyWordsService.registerAndUpdateDailyWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_DAILY_WORD_SUCCESS);
    }
}
