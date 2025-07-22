package com.feelory.feelory_backend.global.exception;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class CustomException extends BaseException {
    public CustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
