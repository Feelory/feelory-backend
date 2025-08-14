package com.feelory.feelory_backend.global.exception.exceptions.auth;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
