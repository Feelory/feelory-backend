package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class WordNotFoundException extends BaseException {

    public WordNotFoundException() {
        super(ErrorCode.WORD_NOT_FOUND);
    }
}
