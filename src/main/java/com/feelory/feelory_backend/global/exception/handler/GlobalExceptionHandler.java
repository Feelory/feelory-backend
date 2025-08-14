package com.feelory.feelory_backend.global.exception.handler;

import com.feelory.feelory_backend.global.api.ApiResponse;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.ExceptionFileLogger;
import com.feelory.feelory_backend.global.exception.dto.model.ValidationDetail;
import com.feelory.feelory_backend.global.exception.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionFileLogger exceptionFileLogger;

    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResponse<Void>> handleNoPageFoundException(Exception e) {

        ErrorCode errorCode = ErrorCode.NOT_FOUND_END_POINT;
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode);

        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException exception) {

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode);

        log.error("handleCustomException() in GlobalExceptionHandler throw BaseException : {}", exception.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {

        exceptionFileLogger.writeToFile(exception);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode);

        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", exception.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }


    /*
        Validation Message 전역 처리
    */

    // @RequestBody 유효성 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ValidationDetail>>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        List<ValidationDetail> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ValidationDetail(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        log.error("Validation Error: {}", errors);

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ApiResponse<List<ValidationDetail>> apiResponse = ApiResponse.error(errorCode, errors);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    // @ModelAttribute 유효성 에러
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<List<ValidationDetail>>> handleBindException(BindException exception) {

        List<ValidationDetail> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ValidationDetail(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        log.error("Validation Error: {}", errors);

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ApiResponse<List<ValidationDetail>> apiResponse = ApiResponse.error(errorCode, errors);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    // @RequestParam, @PathVariable 유효성 에러
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<List<ValidationDetail>>> handleConstraintViolation(ConstraintViolationException exception) {

        List<ValidationDetail> errors = exception.getConstraintViolations().stream()
                .map(err -> new ValidationDetail(err.getPropertyPath().toString(), err.getMessage()))
                .collect(Collectors.toList());

        log.error("Validation Error: {}", errors);

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ApiResponse<List<ValidationDetail>> apiResponse = ApiResponse.error(errorCode, errors);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }
    /*
        메서드 보안 전역 처리
    */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {

        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode);

        log.warn("AccessDeniedException: {}", exception.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }
}
