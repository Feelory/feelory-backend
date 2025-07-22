package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PatchMapping("")
    public ApiResponse<CategoryUpdateResponse> patchCategory(CategoryUpdateRequest request) {
        CategoryUpdateResponse response = categoriesService.modifyCategory(request);

        return ApiResponse.success(response);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @DeleteMapping("")
    public ApiResponse<CategoryDeleteResponse> deleteCategory(CategoryDeleteRequest request) {
        CategoryDeleteResponse response = categoriesService.removeCategory(request);

        return ApiResponse.success(response);
    }
}
