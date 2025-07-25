package com.feelory.feelory_backend.global.exception.exceptions.writings;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class WritingNotFoundException extends BaseException {
    public WritingNotFoundException() {
        super(ErrorCode.WRITING_NOT_FOUND);
    }
}
