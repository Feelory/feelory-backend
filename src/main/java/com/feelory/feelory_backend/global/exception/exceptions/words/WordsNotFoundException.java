package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class WordsNotFoundException extends BaseException {

    public WordsNotFoundException() {
        super(ErrorCode.WORDS_NOT_FOUND);
    }
}
