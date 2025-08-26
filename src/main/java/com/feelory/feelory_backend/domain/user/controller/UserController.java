package com.feelory.feelory_backend.domain.user.controller;

import com.feelory.feelory_backend.domain.user.service.UserService;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "유저 (User)", description = "유저 관련 API 목록")
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Operation(
            summary = "유저 서비스 탈퇴(비활성화)",
            description = "물리가 아닌 논리 삭제(isActive)로 유저를 탈퇴(비활성화) 처리합니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @DeleteMapping(value = "")
    public ApiResponse<Void> deleteUser() {

        jwtProvider.checkUserOrAdmin();

        userService.deleteUser();

        return ApiResponse.success(SuccessCode.DELETE_USER_SUCCESS);
    }
}

