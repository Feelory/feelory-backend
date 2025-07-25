package com.feelory.feelory_backend.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthProperties kakaoOauthProperties;

    public KakaoLoginResponse createKakaoLoginUrl() {
        String url = kakaoOauthProperties.getAuthUri() +
                "?client_id=" + kakaoOauthProperties.getClientId() +
                "&redirect_uri=" + kakaoOauthProperties.getRedirectUri() +
                "&response_type=code";

        return new KakaoLoginResponse(url);
    }
}
