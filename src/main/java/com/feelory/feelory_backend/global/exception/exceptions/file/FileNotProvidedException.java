package com.feelory.feelory_backend.global.exception.exceptions.file;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class FileNotProvidedException extends BaseException {
    public FileNotProvidedException() {
        super(ErrorCode.FILE_NOT_PROVIDED);
    }
}
