package com.feelory.feelory_backend.global.security.auth.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.InvalidPhoneNumberException;
import com.feelory.feelory_backend.global.security.auth.dto.request.LogoutRequest;
import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
import com.feelory.feelory_backend.global.security.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.domain.user.entity.UserTokens;
import com.feelory.feelory_backend.domain.user.entity.Users;
import com.feelory.feelory_backend.domain.user.dto.model.AuthProvider;
import com.feelory.feelory_backend.domain.user.dto.request.RefreshTokenRequest;
import com.feelory.feelory_backend.domain.user.dto.response.RefreshTokenResponse;
import com.feelory.feelory_backend.domain.user.service.UserService;
import com.feelory.feelory_backend.domain.user.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserTokenService userTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse loginOAuth2Kakao(Map<String, Object> attributes) {

        Long kakaoId = ((Number) attributes.get("id")).longValue();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String name = (String) kakaoAccount.get("name");
        String phoneNumber = formatPhoneNumber((String) kakaoAccount.get("phone_number"));

        Users user = userService.findOrCreateUser(name, phoneNumber, AuthProvider.KAKAO, kakaoId);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());
        LocalDateTime accessTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(accessToken);

        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        LocalDateTime refreshTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(refreshToken);

        userTokenService.saveUserToken(user, refreshToken, refreshTokenExp);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .expireAt(accessTokenExp)
                .refreshToken(refreshToken)
                .refreshTokenExpireAt(refreshTokenExp)
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public RefreshTokenResponse reissueToken(RefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        UserTokens userToken = userTokenService.findRefreshToken(oldRefreshToken);

        Users user = userToken.getUser();

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());
        LocalDateTime newAccessTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(newAccessToken);

        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        LocalDateTime newRefreshTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(newRefreshToken);

        userTokenService.rotateRefreshToken(userToken, newRefreshToken, newRefreshTokenExp);

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .expireAt(newAccessTokenExp)
                .refreshToken(newRefreshToken)
                .refreshTokenExpireAt(newRefreshTokenExp)
                .build();
    }

    @Transactional
    public void logout(LogoutRequest request) {
        UserTokens currentUserToken= userTokenService.findRefreshToken(request.getRefreshToken());

        userTokenService.deactivateRefreshToken(currentUserToken);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberException();
        }
        return phoneNumber.replace("+82", "0").replaceAll("[^0-9]", "");
    }
}
