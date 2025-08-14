package com.feelory.feelory_backend.global.exception.exceptions.file;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class UnsupportedImageFormatException extends BaseException {
    public UnsupportedImageFormatException() {
        super(ErrorCode.UNSUPPORTED_IMAGE_FORMAT);
    }
}
