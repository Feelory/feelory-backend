package com.feelory.feelory_backend.global.exception.exceptions.feedback;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class GenerateContentFailedException extends BaseException {
    public GenerateContentFailedException() {
        super(ErrorCode.GENERATE_CONTENT_FAILED);
    }
}