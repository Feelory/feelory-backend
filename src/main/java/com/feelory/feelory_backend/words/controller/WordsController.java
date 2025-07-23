package com.feelory.feelory_backend.words.controller;


import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.WordsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
    TODO. [TR-YOO] Swagger 적용하기
*/
@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordsController {

    private final WordsService wordsService;

    @GetMapping("")
    public ApiResponse<WordListResponse> getWords(WordListRequest request) {
        WordListResponse response = wordsService.getWords(request);

        return ApiResponse.success(response, SuccessCode.GET_WORD_LIST_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PostMapping("")
    public ApiResponse<WordCreateResponse> registerWord(@Valid @RequestBody WordCreateRequest request) {
        WordCreateResponse response = wordsService.registerWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WORD_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PatchMapping("")
    public ApiResponse<WordUpdateResponse> patchWord(@Valid @RequestBody WordUpdateRequest request) {
        WordUpdateResponse response = wordsService.modifyWord(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_WORD_SUCCESS);
    }

}
