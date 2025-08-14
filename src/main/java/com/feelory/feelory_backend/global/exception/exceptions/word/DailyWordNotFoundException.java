package com.feelory.feelory_backend.global.exception.exceptions.word;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class DailyWordNotFoundException extends BaseException {

    public DailyWordNotFoundException() {
        super(ErrorCode.DAILY_WORD_NOT_FOUND);
    }
}
