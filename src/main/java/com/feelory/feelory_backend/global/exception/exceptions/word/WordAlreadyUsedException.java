package com.feelory.feelory_backend.global.exception.exceptions.word;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class WordAlreadyUsedException extends BaseException {

    public WordAlreadyUsedException() {
        super(ErrorCode.WORD_ALREADY_USED);
    }
}
