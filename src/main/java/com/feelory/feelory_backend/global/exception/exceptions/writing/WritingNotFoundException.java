package com.feelory.feelory_backend.global.exception.exceptions.writing;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class WritingNotFoundException extends BaseException {
    public WritingNotFoundException() {
        super(ErrorCode.WRITING_NOT_FOUND);
    }
}
