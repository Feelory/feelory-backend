package com.feelory.feelory_backend.global.exception.exceptions.writings;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class DailyWordConflictException extends BaseException {
    public DailyWordConflictException() {
        super(ErrorCode.WRITING_GOAL_CONFLICT);
    }
}
