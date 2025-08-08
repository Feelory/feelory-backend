package com.feelory.feelory_backend.users.controller;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.api.SuccessCode;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.users.model.response.UserProfileImageResponse;
import com.feelory.feelory_backend.users.service.UserProfileService;
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

    private final JwtTokenProvider jwtTokenProvider;
    private final UserProfileService userProfileService;

    @Operation(
            summary = "유저 프로필 이미지 등록",
            description = "유저 프로필 이미지 등록 API",
            security = {@SecurityRequirement(name = "JWT")}
    )
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserProfileImageResponse> registerProfileImage(@Parameter(description = "업로드할 이미지 파일", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestParam("file")MultipartFile image) {

        jwtTokenProvider.checkUserOrAdmin();

        UserProfileImageResponse response = userProfileService.registerProfileImage(image);

        return ApiResponse.success(response, SuccessCode.REGISTER_USER_PROFILE_IMAGE_SUCCESS);
    }
}
