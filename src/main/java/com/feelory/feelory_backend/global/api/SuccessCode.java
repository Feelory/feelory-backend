package com.feelory.feelory_backend.global.api;

import lombok.Getter;

@Getter
public enum SuccessCode {

    DEFAULT_SUCCESS("SUCCESS", "API 요청 성공");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
