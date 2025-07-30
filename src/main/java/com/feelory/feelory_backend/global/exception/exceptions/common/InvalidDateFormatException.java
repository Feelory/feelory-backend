package com.feelory.feelory_backend.global.exception.exceptions.common;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class InvalidDateFormatException extends BaseException {
    public InvalidDateFormatException() {
        super(ErrorCode.INVALID_DATE_FORMAT);
    }
}
