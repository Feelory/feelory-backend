package com.feelory.feelory_backend.global.exception.exceptions.word;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class DuplicateWritingGoalNameException extends BaseException {
    public DuplicateWritingGoalNameException() {
        super(ErrorCode.DUPLICATE_WRITING_GOAL_NAME);
    }
}
