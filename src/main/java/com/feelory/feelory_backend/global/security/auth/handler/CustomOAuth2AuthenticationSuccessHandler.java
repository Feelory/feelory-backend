    package com.feelory.feelory_backend.global.security.auth.handler;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.feelory.feelory_backend.global.api.ApiResponse;
    import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
    import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
    import com.feelory.feelory_backend.global.exception.exceptions.users.InvalidPhoneNumberException;
    import com.feelory.feelory_backend.global.exception.exceptions.users.UserNotFoundException;
    import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
    import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
    import com.feelory.feelory_backend.users.entity.UserTokens;
    import com.feelory.feelory_backend.users.entity.Users;
    import com.feelory.feelory_backend.users.repository.UserTokensRepository;
    import com.feelory.feelory_backend.users.repository.UsersRepository;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.annotation.Transactional;

    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.util.Map;

    @Component
    @RequiredArgsConstructor
    public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        private final JwtTokenProvider jwtTokenProvider;
        private final UsersRepository usersRepository;
        private final UserTokensRepository userTokensRepository;
        private final ObjectMapper objectMapper;

        @Override
        @Transactional
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            try {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

                Map<String, Object> attributes = oAuth2User.getAttributes();
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                String phoneNumber = formatPhoneNumber((String) kakaoAccount.get("phone_number"));

                Users user = usersRepository.findByPhoneNumberAndIsActive(phoneNumber, true)
                        .orElseThrow(UserNotFoundException::new);

                String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());
                LocalDateTime accessTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(accessToken);

                String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
                LocalDateTime refreshTokenExp = jwtTokenProvider.getExpirationLocalDateTimeFromToken(refreshToken);

                LoginResponse loginResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .expireAt(accessTokenExp)
                        .refreshToken(refreshToken)
                        .refreshTokenExpireAt(refreshTokenExp)
                        .nickname(user.getNickname())
                        .build();

                UserTokens newTokens = createUserTokens(user, refreshToken, refreshTokenExp);
                userTokensRepository.save(newTokens);

                ApiResponse<LoginResponse> apiResponse = ApiResponse.success(loginResponse);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            }  catch (BaseException e) {
                writeResponse(response, e.getErrorCode().getStatus(), ApiResponse.error(e.getErrorCode()));
            } catch (Exception e) {
                writeResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
            }
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

        private String formatPhoneNumber(String phoneNumber) {
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                throw new InvalidPhoneNumberException();
            }
            return phoneNumber.replace("+82", "0")
                    .replaceAll("[^0-9]", "");
        }

        private void writeResponse(HttpServletResponse response, HttpStatus status, ApiResponse<?> apiResponse) throws IOException {
            response.setStatus(status.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }
