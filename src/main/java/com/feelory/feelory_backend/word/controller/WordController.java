package com.feelory.feelory_backend.word.controller;


import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.word.dto.request.WordCreateRequest;
import com.feelory.feelory_backend.word.dto.request.WordDeleteRequest;
import com.feelory.feelory_backend.word.dto.request.WordListRequest;
import com.feelory.feelory_backend.word.dto.request.WordUpdateRequest;
import com.feelory.feelory_backend.word.dto.response.WordCreateResponse;
import com.feelory.feelory_backend.word.dto.response.WordListResponse;
import com.feelory.feelory_backend.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
@Tag(name = "단어 (Words)", description = "단어 API 목록")
public class WordController {

    private final WordService wordService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "모든 단어 목록",
            description = "조건에 따른 모든 단어 목록 조회 API"
    )
    @GetMapping("")
    public ApiResponse<WordListResponse> getWords(@ParameterObject WordListRequest request) {
        WordListResponse response = wordService.getWords(request);

        return ApiResponse.success(response, SuccessCode.GET_WORD_LIST_SUCCESS);
    }

    @Operation(
            summary = "단어 추가",
            description = "단어 추가 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("")
    public ApiResponse<WordCreateResponse> registerWord(@Valid @RequestBody WordCreateRequest request) {
        jwtTokenProvider.checkAdmin();

        WordCreateResponse response = wordService.registerWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WORD_SUCCESS);
    }

    @Operation(
            summary = "단어 수정",
            description = "단어 수정 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PatchMapping("")
    public ApiResponse<Void> patchWord(@Valid @RequestBody WordUpdateRequest request) {
        jwtTokenProvider.checkAdmin();

        wordService.modifyWord(request);

        return ApiResponse.success(SuccessCode.UPDATE_WORD_SUCCESS);
    }

    @Operation(
            summary = "단어 삭제",
            description = "단어 삭제 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @DeleteMapping("")
    public ApiResponse<Void> deleteWord(@Valid WordDeleteRequest request) {
        jwtTokenProvider.checkAdmin();

        wordService.removeWord(request);

        return ApiResponse.success(SuccessCode.DELETE_WORD_SUCCESS);
    }
}
