package com.feelory.feelory_backend.global.exception.exceptions.handler;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorResponse;
import com.feelory.feelory_backend.global.exception.exceptions.ExceptionFileLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.NoHandlerFoundException;


@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionFileLogger exceptionFileLogger;

    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNoPageFoundException(Exception e) {

        ErrorCode errorCode = ErrorCode.NOT_FOUND_END_POINT;
        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBaseException(BaseException exception) {

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception exception) {

        exceptionFileLogger.writeToFile(exception);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }
}
