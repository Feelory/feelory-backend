package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.service.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/word-categories")
@RequiredArgsConstructor
@Tag(name = "단어 카테고리 (Word Categories)", description = "단어 카테고리 API 목록")
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "모든 카테고리 목록",
            description = "조건에 따른 모든 단어 카테고리 목록 조회 API"
    )
    @GetMapping("")
    public ApiResponse<CategoryListResponse> getCategories(@ParameterObject CategoryListRequest request) {
        CategoryListResponse response = categoriesService.getCategories(request);

        return ApiResponse.success(response, SuccessCode.GET_CATEGORY_LIST_SUCCESS);
    }

    @Operation(
            summary = "카테고리 추가",
            description = "단어 카테고리 추가 API"
    )
    @PostMapping("")
    public ApiResponse<CategoryCreateResponse> postCategory(@Valid @RequestBody CategoryCreateRequest request) {
        jwtTokenProvider.checkAdmin();

        CategoryCreateResponse response = categoriesService.registerCategory(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_CATEGORY_SUCCESS);
    }

    @Operation(
            summary = "카테고리 수정",
            description = "단어 카테고리 수정 API"
    )
    @PatchMapping("")
    public ApiResponse<CategoryUpdateResponse> patchCategory(@Valid @RequestBody CategoryUpdateRequest request) {
        jwtTokenProvider.checkAdmin();

        CategoryUpdateResponse response = categoriesService.modifyCategory(request);

        return ApiResponse.success(response, SuccessCode.UPDATE_CATEGORY_SUCCESS);
    }

    @Operation(
            summary = "카테고리 제거",
            description = "단어 카테고리 제거(비활성화) API"
    )
    @DeleteMapping("")
    public ApiResponse<CategoryDeleteResponse> deleteCategory(@Valid CategoryDeleteRequest request) {
        jwtTokenProvider.checkAdmin();

        CategoryDeleteResponse response = categoriesService.removeCategory(request);

        return ApiResponse.success(response, SuccessCode.DELETE_CATEGORY_SUCCESS);
    }
}
