package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class DuplicateWritingGoalNameException extends BaseException {
    public DuplicateWritingGoalNameException() {
        super(ErrorCode.DUPLICATE_WRITING_GOAL_NAME);
    }
}
