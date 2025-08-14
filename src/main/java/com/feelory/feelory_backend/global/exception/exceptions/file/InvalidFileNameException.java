package com.feelory.feelory_backend.global.exception.exceptions.file;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class InvalidFileNameException extends BaseException {
    public InvalidFileNameException() {
        super(ErrorCode.INVALID_FILE_NAME);
    }
}
