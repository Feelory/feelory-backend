package com.feelory.feelory_backend.global.exception.exceptions.users;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class InvalidPhoneNumberException extends BaseException {
    public InvalidPhoneNumberException() {
        super(ErrorCode.INVALID_PHONE_NUMBER);
    }
}
