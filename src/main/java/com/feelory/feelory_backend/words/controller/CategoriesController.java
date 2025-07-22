package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.words.model.CategoryCreateRequest;
import com.feelory.feelory_backend.words.model.CategoryCreateResponse;
import com.feelory.feelory_backend.words.model.CategoryListRequest;
import com.feelory.feelory_backend.words.model.CategoryListResponse;
import com.feelory.feelory_backend.words.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    TODO. [TR-YOO] Swagger 적용하기
*/
@RestController
@RequestMapping("/api/word-categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping("")
    public ApiResponse<CategoryListResponse> getCategories(CategoryListRequest request) {
        CategoryListResponse response = categoriesService.getCategories(request);

        return ApiResponse.success(response);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PostMapping("")
    public ApiResponse<CategoryCreateResponse> postCategory(CategoryCreateRequest request) {
        CategoryCreateResponse response = categoriesService.registerCategory(request);

        return ApiResponse.success(response);
    }
}
