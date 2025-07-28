    package com.feelory.feelory_backend.global.security.auth.handler;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.feelory.feelory_backend.global.api.ApiResponse;
    import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
    import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
    import com.feelory.feelory_backend.global.security.auth.dto.response.LoginResponse;
    import com.feelory.feelory_backend.global.security.auth.service.AuthService;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;

    import java.io.IOException;
    @Component
    @RequiredArgsConstructor
    public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        private final ObjectMapper objectMapper;
        private final AuthService authService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException {
            try {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

                LoginResponse loginResponse = authService.loginOAuth2Kakao(oAuth2User.getAttributes());

                ApiResponse<LoginResponse> apiResponse = ApiResponse.success(loginResponse);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            }  catch (BaseException e) {
                writeResponse(response, e.getErrorCode().getStatus(), ApiResponse.error(e.getErrorCode()));
            } catch (Exception e) {
                writeResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
            }
        }

        private void writeResponse(HttpServletResponse response, HttpStatus status, ApiResponse<?> apiResponse) throws IOException {
            response.setStatus(status.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }
