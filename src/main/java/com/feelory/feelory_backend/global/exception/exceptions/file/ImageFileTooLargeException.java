package com.feelory.feelory_backend.global.exception.exceptions.file;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class ImageFileTooLargeException extends BaseException {
    public ImageFileTooLargeException() {
        super(ErrorCode.IMAGE_FILE_TOO_LARGE);
    }
}
