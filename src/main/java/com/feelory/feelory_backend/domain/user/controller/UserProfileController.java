package com.feelory.feelory_backend.domain.user.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileImageResponse;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileResponse;
import com.feelory.feelory_backend.domain.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
@Tag(name = "유저 프로필 (UserProfile)", description = "유저 프로필 관련 API 목록")
public class UserProfileController {

    private final JwtProvider jwtProvider;
    private final UserProfileService userProfileService;

    @Operation(
            summary = "유저 프로필 이미지 갱신(생성/교체)",
            description = "기존 이미지가 없으면 생성, 있으면 교체",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PutMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserProfileImageResponse> updateProfileImage(@Parameter(description = "업로드할 이미지 파일", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestParam("file") MultipartFile image) {

        jwtProvider.checkUserOrAdmin();

        UserProfileImageResponse response = userProfileService.updateProfileImage(image);

        return ApiResponse.success(response, SuccessCode.REGISTER_USER_PROFILE_IMAGE_SUCCESS);
    }

    @Operation(
            summary = "내 프로필 조회",
            description = "글 관련 정보 추후 추가 예정",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @GetMapping(value = "/me")
    public ApiResponse<UserProfileResponse> updateProfileImage() {
        jwtProvider.checkUserOrAdmin();

        UserProfileResponse response = userProfileService.readUserProfile();

        return ApiResponse.success(response, SuccessCode.READ_USER_PROFILE_SUCCESS);
    }
}
