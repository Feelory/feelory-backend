package com.feelory.feelory_backend.words.controller;


import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.words.model.WordCreateRequest;
import com.feelory.feelory_backend.words.model.WordCreateResponse;
import com.feelory.feelory_backend.words.model.WordListRequest;
import com.feelory.feelory_backend.words.model.WordListResponse;
import com.feelory.feelory_backend.words.service.WordsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ApiResponse<WordCreateResponse> registerWord(@Valid @RequestBody WordCreateRequest request) {
        WordCreateResponse response = wordsService.registerWord(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_WORD_SUCCESS);
    }

}
