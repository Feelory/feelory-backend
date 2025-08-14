package com.feelory.feelory_backend.domain.word.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.exception.exceptions.common.InvalidDateFormatException;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.domain.word.dto.request.DailyWordCreateRequest;
import com.feelory.feelory_backend.domain.word.dto.request.DailyWordDeleteRequest;
import com.feelory.feelory_backend.domain.word.dto.request.DailyWordUpdateRequest;
import com.feelory.feelory_backend.domain.word.dto.response.DailyWordCreateResponse;
import com.feelory.feelory_backend.domain.word.dto.response.DailyWordDetailResponse;
import com.feelory.feelory_backend.domain.word.service.DailyWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class DailyWordController {

    private final DailyWordService dailyWordService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "특정 날짜의 오늘의 단어 조회",
            description = "특정 날짜의 오늘의 단어 조회 API",
            security = {@SecurityRequirement(name = "JWT")}

    )
    @GetMapping("/{topicDate}")
    public ApiResponse<DailyWordDetailResponse> getDailyWord(@PathVariable String topicDate) {
        LocalDateTime parsed;

        try {
            parsed = LocalDateTime.parse(topicDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        DailyWordDetailResponse response = dailyWordService.getDailyWord(parsed);

        return ApiResponse.success(response, SuccessCode.GET_DAILY_WORD_SUCCESS);
    }

    @Operation(
            summary = "오늘의 단어 조회",
            description = "오늘의 단어 조회 API"

    )
    @GetMapping("/today")
    public ApiResponse<DailyWordDetailResponse> getDailyWordToday() {
        DailyWordDetailResponse response = dailyWordService.getDailyWordToday();

        return ApiResponse.success(response, SuccessCode.GET_TODAY_DAILY_WORD_SUCCESS);
    }

    @Operation(
            summary = "오늘의 단어 추가",
            description = "오늘의 단어 추가 API",
            security = {@SecurityRequirement(name = "JWT")}

    )
    @PostMapping("")
    public ApiResponse<DailyWordCreateResponse> postDailyWord(@Valid @RequestBody DailyWordCreateRequest request) {
        jwtProvider.checkAdmin();

        DailyWordCreateResponse response = dailyWordService.registerDailyWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_DAILY_WORD_SUCCESS);
    }

    @Operation(
            summary = "오늘의 단어 수정",
            description = "오늘의 단어 수정 API",
            security = {@SecurityRequirement(name = "JWT")}

    )
    @PatchMapping("")
    public ApiResponse<Void> patchDailyWord(@Valid @RequestBody DailyWordUpdateRequest request) {

        jwtProvider.checkAdmin();

        dailyWordService.modifyDailyWord(request);

        return ApiResponse.success(SuccessCode.UPDATE_DAILY_WORD_SUCCESS);
    }

    @Operation(
            summary = "오늘의 단어 삭제",
            description = "오늘의 단어 삭제 API",
            security = {@SecurityRequirement(name = "JWT")}

    )
    @DeleteMapping("")
    public ApiResponse<Void> deleteDailyWord(@Valid DailyWordDeleteRequest request) {

        jwtProvider.checkAdmin();

        dailyWordService.removeDailyWord(request);

        return ApiResponse.success(SuccessCode.DELETE_DAILY_WORD_SUCCESS);
    }
}
