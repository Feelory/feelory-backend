package com.feelory.feelory_backend.global.exception.exceptions.file;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class FileSaveFailedException extends BaseException {
    public FileSaveFailedException() {
        super(ErrorCode.FILE_SAVE_FAILED);
    }
}