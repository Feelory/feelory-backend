package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class InvalidTopicDateException extends BaseException {
    public InvalidTopicDateException() {
        super(ErrorCode.DUPLICATE_WORD_NAME);
    }
}
