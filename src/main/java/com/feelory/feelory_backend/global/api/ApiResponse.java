package com.feelory.feelory_backend.global.api;

import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorResponse;
import com.feelory.feelory_backend.global.exception.exceptions.ValidationDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private HttpStatus status;
    private String code;
    private String message;
    private T data;

    @Builder
    public ApiResponse(SuccessCode successCode, T data) {
        this.status = successCode.getStatus();
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.data = data;
    }

    @Builder
    public ApiResponse(ErrorResponse errorResponse, T data) {
        this.status = errorResponse.getStatus();
        this.code = errorResponse.getCode();
        this.message = errorResponse.getMessage();
        this.data = data;
    }

    /*
        Success
    */
    public static <T> ApiResponse<T> success() {
        SuccessCode successCode = SuccessCode.DEFAULT_SUCCESS;
        return new ApiResponse<>(successCode, null);
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return new ApiResponse<>(successCode, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        SuccessCode successCode = SuccessCode.DEFAULT_SUCCESS;
        return new ApiResponse<>(successCode,  data);
    }

    public static <T> ApiResponse<T> success(T data, SuccessCode successCode) {
        return new ApiResponse<>(successCode,  data);
    }


    /*
        Error
    */

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ApiResponse<>(errorResponse, null);
    }

    // Validation 체크 시 유효성 검사 메세지 전달하기 위해 사용
    public static <T> ApiResponse<T> error(ErrorCode errorCode, T errors) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ApiResponse<>(errorResponse, errors);
    }
}
