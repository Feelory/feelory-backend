package com.feelory.feelory_backend.global.security.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private LocalDateTime expireAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpireAt;
    private String nickname;
}