package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.exception.exceptions.words.InvalidDateFormatException;
import com.feelory.feelory_backend.words.docs.DailyWordsDocs;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.DailyWordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/daily-words")
@RequiredArgsConstructor
@Tag(name = "오늘의 단어 (DailyWords)", description = "오늘의 단어 API 목록")
public class DailyWordsController {

    private final DailyWordsService dailyWordsService;

    @GetMapping("/{topicDate}")
    public ApiResponse<DailyWordDetailResponse> getDailyWord(@PathVariable String topicDate) {
        LocalDateTime parsed;

        try {
            parsed = LocalDateTime.parse(topicDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        DailyWordDetailResponse response = dailyWordsService.getDailyWord(parsed);

        return ApiResponse.success(response, SuccessCode.GET_DAILY_WORD_SUCCESS);
    }

    @GetMapping("/today")
    public ApiResponse<DailyWordDetailResponse> getDailyWordToday() {
        DailyWordDetailResponse response = dailyWordsService.getDailyWordToday();

        return ApiResponse.success(response, SuccessCode.GET_TODAY_DAILY_WORD_SUCCESS);
    }

    @Operation(
            summary = "오늘의 단어 추가",
            description = DailyWordsDocs.POST_DAILY_WORD_DESCRIPTION
    )
    @PostMapping("")
    public ApiResponse<DailyWordCreateResponse> postDailyWord(@Valid @RequestBody DailyWordCreateRequest request) {
        DailyWordCreateResponse response = dailyWordsService.registerAndUpdateDailyWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_DAILY_WORD_SUCCESS);
    }

    @PatchMapping("")
    public ApiResponse<DailyWordUpdateResponse> patchDailyWord(@Valid @RequestBody DailyWordUpdateRequest request) {

        DailyWordUpdateResponse response = dailyWordsService.modifyDailyWord(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_DAILY_WORD_SUCCESS);
    }
}
