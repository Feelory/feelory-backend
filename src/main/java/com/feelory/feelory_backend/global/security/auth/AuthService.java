package com.feelory.feelory_backend.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthProperties kakaoOauthProperties;
    private final WebClient webClient = WebClient.create();

    public KakaoLoginResponse createKakaoLoginUrl() {
        String url = kakaoOauthProperties.getAuthUri() +
                "?client_id=" + kakaoOauthProperties.getClientId() +
                "&redirect_uri=" + kakaoOauthProperties.getRedirectUri() +
                "&response_type=code";

        return new KakaoLoginResponse(url);
    }

    public KakaoAccessTokenResponse getKakaoAccessToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=authorization_code" +
                        "&client_id=" + kakaoOauthProperties.getClientId()  +
                        "&redirect_uri=" + kakaoOauthProperties.getRedirectUri() +
                        "&code=" + code)
                .retrieve()
                .bodyToMono(KakaoAccessTokenResponse.class)
                .block();
    }
}
