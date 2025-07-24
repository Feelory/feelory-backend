package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class DailyWordNotFoundException extends BaseException {

    public DailyWordNotFoundException() {
        super(ErrorCode.DAILY_WORD_NOT_FOUND);
    }
}
