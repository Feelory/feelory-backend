package com.feelory.feelory_backend.global.exception.exceptions.word;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class DuplicateWordNameException extends BaseException {
    public DuplicateWordNameException() {
        super(ErrorCode.DUPLICATE_WORD_NAME);
    }
}
