package com.feelory.feelory_backend.global.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        log.info("권한 없는 사용자 접근 차단 - URI: {}, Method: {}", request.getRequestURI(), request.getMethod());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<Void> apiResponse = ApiResponse.error(ErrorCode.ACCESS_DENIED);
        String json = new ObjectMapper().writeValueAsString(apiResponse);

        response.getWriter().write(json);
    }
}
