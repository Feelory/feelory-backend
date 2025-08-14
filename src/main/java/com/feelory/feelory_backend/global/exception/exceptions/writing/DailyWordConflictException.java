package com.feelory.feelory_backend.global.exception.exceptions.writing;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class DailyWordConflictException extends BaseException {
    public DailyWordConflictException() {
        super(ErrorCode.DAILY_WORD_CONFLICT);
    }
}
