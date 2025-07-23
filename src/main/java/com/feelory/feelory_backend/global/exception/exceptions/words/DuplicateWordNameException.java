package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class DuplicateWordNameException extends BaseException {
    public DuplicateWordNameException() {
        super(ErrorCode.DUPLICATE_WORD_NAME);
    }
}
