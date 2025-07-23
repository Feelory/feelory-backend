package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.CategoriesService;
import jakarta.validation.Valid;
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

        return ApiResponse.success(response, SuccessCode.GET_CATEGORY_LIST_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PostMapping("")
    public ApiResponse<CategoryCreateResponse> postCategory(@Valid @RequestBody CategoryCreateRequest request) {
        CategoryCreateResponse response = categoriesService.registerCategory(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_CATEGORY_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @PatchMapping("")
    public ApiResponse<CategoryUpdateResponse> patchCategory(@Valid @RequestBody CategoryUpdateRequest request) {
        CategoryUpdateResponse response = categoriesService.modifyCategory(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_CATEGORY_SUCCESS);
    }

    /*
        TODO. [TR-YOO] Admin 검증 로직 추가 필요
    */
    @DeleteMapping("")
    public ApiResponse<CategoryDeleteResponse> deleteCategory(@Valid CategoryDeleteRequest request) {
        CategoryDeleteResponse response = categoriesService.removeCategory(request);

        return ApiResponse.success(response, SuccessCode.DELETE_CATEGORY_SUCCESS);
    }
}
