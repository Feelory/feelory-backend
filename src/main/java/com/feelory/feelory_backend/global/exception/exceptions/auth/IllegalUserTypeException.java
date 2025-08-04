package com.feelory.feelory_backend.global.exception.exceptions.auth;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class IllegalUserTypeException extends BaseException {
    public IllegalUserTypeException() {
        super(ErrorCode.ILLEGAL_USER_TYPE);
    }
}
