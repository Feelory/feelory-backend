package com.feelory.feelory_backend.global.security.auth.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.user.entity.UserToken;
import com.feelory.feelory_backend.global.exception.exceptions.user.InvalidPhoneNumberException;
import com.feelory.feelory_backend.global.security.auth.dto.request.LogoutRequest;
import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse loginOAuth2Kakao(Map<String, Object> attributes) {

        Long kakaoId = ((Number) attributes.get("id")).longValue();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String name = (String) kakaoAccount.get("name");
        String phoneNumber = formatPhoneNumber((String) kakaoAccount.get("phone_number"));

        User user = userService.findOrCreateUser(name, phoneNumber, AuthProvider.KAKAO, kakaoId);

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        LocalDateTime accessTokenExp = jwtProvider.getExpirationLocalDateTimeFromToken(accessToken);

        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        LocalDateTime refreshTokenExp = jwtProvider.getExpirationLocalDateTimeFromToken(refreshToken);

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

        UserToken userToken = userTokenService.findRefreshToken(oldRefreshToken);

        User user = userToken.getUser();

        String newAccessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        LocalDateTime newAccessTokenExp = jwtProvider.getExpirationLocalDateTimeFromToken(newAccessToken);

        String newRefreshToken = jwtProvider.generateRefreshToken(user.getId());
        LocalDateTime newRefreshTokenExp = jwtProvider.getExpirationLocalDateTimeFromToken(newRefreshToken);

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
        UserToken currentUserToken= userTokenService.findRefreshToken(request.getRefreshToken());

        userTokenService.deactivateRefreshToken(currentUserToken);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberException();
        }
        return phoneNumber.replace("+82", "0").replaceAll("[^0-9]", "");
    }
}
