package com.feelory.feelory_backend.global.security.auth.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.InvalidPhoneNumberException;
import com.feelory.feelory_backend.global.security.auth.dto.response.KakaoAccessTokenResponse;
import com.feelory.feelory_backend.global.security.auth.dto.response.KakaoLoginResponse;
import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.global.security.auth.properties.KakaoOauthProperties;
import com.feelory.feelory_backend.global.security.auth.dto.response.KakaoUserInfoResponse;
import com.feelory.feelory_backend.users.entity.UserTokens;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.model.AuthProvider;
import com.feelory.feelory_backend.users.model.UserRole;
import com.feelory.feelory_backend.users.repository.UserTokensRepository;
import com.feelory.feelory_backend.users.repository.UsersRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthProperties kakaoOauthProperties;
    private final UsersRepository usersRepository;
    private final UserTokensRepository userTokensRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebClient webClient = WebClient.create();

    public KakaoLoginResponse createKakaoLoginUrl() {
        String url = kakaoOauthProperties.getAuthUri() +
                "?client_id=" + kakaoOauthProperties.getClientId() +
                "&redirect_uri=" + kakaoOauthProperties.getRedirectUri() +
                "&response_type=code";

        return new KakaoLoginResponse(url);
    }

    public LoginResponse loginWithKakaoCode(String code) {

        // 1. 인가 코드로 토큰 발급
        KakaoAccessTokenResponse tokenResponse = getKakaoAccessToken(code);

        // 2. 토큰으로 카카오 사용자 정보 조회
        KakaoUserInfoResponse userInfo = getUserInfo(tokenResponse.getAccessToken());

        String userName = userInfo.getKakaoAccount().getName();
        String phoneNumber = formatPhoneNumber(userInfo.getKakaoAccount().getPhoneNumber());
        Long providerUserId = userInfo.getId();

        // 3. 유저 조회(휴대포번호 기준) 또는 생성
        Users user = usersRepository.findByPhoneNumberAndIsActive(phoneNumber, true)
                .orElseGet(() -> usersRepository.save(createUsers(userName, phoneNumber, AuthProvider.KAKAO, providerUserId)));

        // 4. JWT 발급
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());
        LocalDateTime accessTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(accessToken);

        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        LocalDateTime refreshTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(refreshToken);

        // 5. 새로운 토큰 저장
        UserTokens newTokens = createUserTokens(user, refreshToken, refreshTokenExp);
        userTokensRepository.save(newTokens);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .expireAt(accessTokenExp)
                .refreshToken(refreshToken)
                .refreshTokenExpireAt(refreshTokenExp)
                .nickname(user.getNickname())
                .build();
    }

    private UserTokens createUserTokens(Users user, String refreshToken, LocalDateTime refreshTokenExp) {
        UserTokens token = UserTokens.builder()
                .refreshToken(refreshToken)
                .refreshTokenExp(refreshTokenExp)
                .isActive(true)
                .build();
        user.addUserToken(token);
        return token;
    }

    private Users createUsers(String userName, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return Users.builder()
                .name(userName)
                // TODO. 랜덤 닉네임 (ex. 신난다람쥐7843)
                .nickname("임시닉네임" + (int) (Math.random() * 10000))
                .phoneNumber(phoneNumber)
                .role(UserRole.USER)
                .authProvider(authProvider)
                .providerUserId(providerUserId)
                .isActive(true)
                .build();
    }

    private KakaoAccessTokenResponse getKakaoAccessToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=authorization_code" +
                        "&client_id=" + kakaoOauthProperties.getClientId() +
                        "&redirect_uri=" + kakaoOauthProperties.getRedirectUri() +
                        "&code=" + code)
                .retrieve()
                .bodyToMono(KakaoAccessTokenResponse.class)
                .block();
    }

    private KakaoUserInfoResponse getUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberException();
        }

        //한국 번호(+82)만 국내형으로 포맷됨
        return phoneNumber.replace("+82", "0")
                .replaceAll("[^0-9]", "");
    }

}
