package com.feelory.feelory_backend.global.exception.exceptions.writings;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class WritingGoalConflictException extends BaseException {
    public WritingGoalConflictException() {
        super(ErrorCode.WRITING_GOAL_CONFLICT);
    }
}
