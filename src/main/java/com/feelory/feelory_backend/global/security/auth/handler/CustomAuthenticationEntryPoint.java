package com.feelory.feelory_backend.global.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.info("인증되지 않은 사용자 접근 차단 - URI: {}, Method: {}, Message: {}",
                request.getRequestURI(), request.getMethod(), authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<Void> apiResponse = ApiResponse.error(ErrorCode.AUTHENTICATION_FAILED);
        String json = new ObjectMapper().writeValueAsString(apiResponse);

        response.getWriter().write(json);
    }
}
