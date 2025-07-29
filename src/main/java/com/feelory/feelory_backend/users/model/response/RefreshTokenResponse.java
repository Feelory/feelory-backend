package com.feelory.feelory_backend.users.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    private String accessToken;
    private LocalDateTime expireAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpireAt;
}
