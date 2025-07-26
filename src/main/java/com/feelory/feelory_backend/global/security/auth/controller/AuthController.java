package com.feelory.feelory_backend.global.security.auth.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.dto.response.KakaoLoginResponse;
import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
import com.feelory.feelory_backend.global.security.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증(Auth)", description = "인증 API 목록")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ApiResponse<KakaoLoginResponse> kakaoLogin() {
        KakaoLoginResponse kakaoLoginResponse = authService.createKakaoLoginUrl();

        return ApiResponse.success(kakaoLoginResponse, SuccessCode.CREATE_KAKAO_LOGIN_URL_SUCCESS);
    }

    @GetMapping("/kakao/callback")
    public ApiResponse<LoginResponse> kakaoCallback(@RequestParam("code") String code) {
        LoginResponse loginResponse = authService.loginWithKakaoCode(code);

        return ApiResponse.success(loginResponse,SuccessCode.LOGIN_KAKAO_SUCCESS);
    }
}
