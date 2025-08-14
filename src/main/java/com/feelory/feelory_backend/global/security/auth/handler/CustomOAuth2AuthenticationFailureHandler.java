package com.feelory.feelory_backend.global.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.info("OAuth2 로그인 실패 - URI: {}, Method: {}",
                request.getRequestURI(), request.getMethod());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<Void> apiResponse = ApiResponse.error(ErrorCode.AUTHENTICATION_FAILED);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
