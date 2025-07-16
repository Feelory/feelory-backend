package com.feelory.feelory_backend.words.controller;

import com.feelory.feelory_backend.words.model.CategoriesRequest;
import com.feelory.feelory_backend.words.model.CategoriesResponse;
import com.feelory.feelory_backend.words.service.CategoriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    TODO. [TR-YOO] 공통 Response 객체 적용하기
    TODO. [TR-YOO] Swagger 적용하기
*/
@RestController
@RequestMapping("/api/word-categories")
public class CategoriesController {

    CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("")
    public CategoriesResponse getCategories(CategoriesRequest request) {
        CategoriesResponse response = categoriesService.getCategories(request);

        return response;
    }
}
