package com.feelory.feelory_backend.global.exception.exceptions.handler;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.ExceptionFileLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionFileLogger exceptionFileLogger;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleBaseException(BaseException exception) {

        ErrorCode errorCode = exception.getErrorCode();
//        ErrorResponse errorResponse = new ErrorResponse(code);
//        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorResponse);

        return ResponseEntity
                .status(errorCode.getStatus())
//                .body(apiResponse);
                .body(errorCode.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {

        exceptionFileLogger.writeToFile(exception);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
//        ErrorResponse errorResponse = new ErrorResponse(code);
//        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorResponse);

        return ResponseEntity
                .status(errorCode.getStatus())
//                .body(apiResponse);
                .body(errorCode.getMessage());
    }
}
