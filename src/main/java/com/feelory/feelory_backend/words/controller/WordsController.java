package com.feelory.feelory_backend.words.controller;


import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.WordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
@Tag(name = "단어 (Words)", description = "단어 API 목록")
public class WordsController {

    private final WordsService wordsService;

    @Operation(
            summary = "모든 단어 목록",
            description = "조건에 따른 모든 단어 목록 조회 API"
    )
    @GetMapping("")
    public ApiResponse<WordListResponse> getWords(WordListRequest request) {
        WordListResponse response = wordsService.getWords(request);

        return ApiResponse.success(response, SuccessCode.GET_WORD_LIST_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @Operation(
            summary = "단어 추가",
            description = "단어 추가 API"
    )
    @PostMapping("")
    public ApiResponse<WordCreateResponse> registerWord(@Valid @RequestBody WordCreateRequest request) {
        WordCreateResponse response = wordsService.registerWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WORD_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @Operation(
            summary = "단어 수정",
            description = "단어 수정 API"
    )
    @PatchMapping("")
    public ApiResponse<WordUpdateResponse> patchWord(@Valid @RequestBody WordUpdateRequest request) {
        WordUpdateResponse response = wordsService.modifyWord(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_WORD_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @Operation(
            summary = "단어 삭제",
            description = "단어 삭제 API"
    )
    @DeleteMapping("")
    public ApiResponse<WordDeleteResponse> deleteWord(@Valid WordDeleteRequest request) {
        WordDeleteResponse response = wordsService.removeWord(request);

        return ApiResponse.success(response, SuccessCode.DELETE_WORD_SUCCESS);
    }
}
