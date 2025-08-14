package com.feelory.feelory_backend.global.security.auth.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.dto.request.LogoutRequest;
import com.feelory.feelory_backend.global.security.auth.service.AuthService;
import com.feelory.feelory_backend.domain.user.dto.request.RefreshTokenRequest;
import com.feelory.feelory_backend.domain.user.dto.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증 (Auth)", description = "인증 관련 API 목록")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "JWT 재발급",
            description = "RefreshToken을 서버에 제출하여 새로운 AccessToken과 RefreshToken을 재발급받습니다.\n" +
                    "기존의 RefreshToken은 재발급 이후 폐기됩니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("/refresh-token")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<RefreshTokenResponse> reissueToken(@RequestBody RefreshTokenRequest request) {

        RefreshTokenResponse refreshTokenResponse = authService.reissueToken(request);

        return ApiResponse.success(refreshTokenResponse, SuccessCode.REISSUE_TOKENS_SUCCESS);
    }

    @Operation(
            summary = "로그아웃 API",
            description = "해당 요청시 DB의 RefreshToken을 폐기합니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) {

        authService.logout(request);
        return ApiResponse.success(null, SuccessCode.LOGOUT_SUCCESS);
    }
}
