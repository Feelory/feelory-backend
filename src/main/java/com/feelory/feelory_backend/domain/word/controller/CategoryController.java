package com.feelory.feelory_backend.domain.word.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.domain.word.dto.request.CategoryCreateRequest;
import com.feelory.feelory_backend.domain.word.dto.request.CategoryDeleteRequest;
import com.feelory.feelory_backend.domain.word.dto.request.CategoryListRequest;
import com.feelory.feelory_backend.domain.word.dto.request.CategoryUpdateRequest;
import com.feelory.feelory_backend.domain.word.dto.response.CategoryCreateResponse;
import com.feelory.feelory_backend.domain.word.dto.response.CategoryListResponse;
import com.feelory.feelory_backend.domain.word.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/word-categories")
@RequiredArgsConstructor
@Tag(name = "단어 카테고리 (Word Categories)", description = "단어 카테고리 API 목록")
public class CategoryController {

    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "모든 카테고리 목록",
            description = "조건에 따른 모든 단어 카테고리 목록 조회 API"
    )
    @GetMapping("")
    public ApiResponse<CategoryListResponse> getCategories(@ParameterObject CategoryListRequest request) {
        CategoryListResponse response = categoryService.getCategories(request);

        return ApiResponse.success(response, SuccessCode.GET_CATEGORY_LIST_SUCCESS);
    }

    @Operation(
            summary = "카테고리 추가",
            description = "단어 카테고리 추가 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("")
    public ApiResponse<CategoryCreateResponse> postCategory(@Valid @RequestBody CategoryCreateRequest request) {
        jwtTokenProvider.checkAdmin();

        CategoryCreateResponse response = categoryService.registerCategory(request);

        return ApiResponse.success(response, SuccessCode.REGISTER_CATEGORY_SUCCESS);
    }

    @Operation(
            summary = "카테고리 수정",
            description = "단어 카테고리 수정 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PatchMapping("")
    public ApiResponse<Void> patchCategory(@Valid @RequestBody CategoryUpdateRequest request) {
        jwtTokenProvider.checkAdmin();

        categoryService.modifyCategory(request);

        return ApiResponse.success(SuccessCode.UPDATE_CATEGORY_SUCCESS);
    }

    @Operation(
            summary = "카테고리 제거",
            description = "단어 카테고리 제거(비활성화) API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @DeleteMapping("")
    public ApiResponse<Void> deleteCategory(@Valid CategoryDeleteRequest request) {
        jwtTokenProvider.checkAdmin();

        categoryService.removeCategory(request);

        return ApiResponse.success(SuccessCode.DELETE_CATEGORY_SUCCESS);
    }
}
