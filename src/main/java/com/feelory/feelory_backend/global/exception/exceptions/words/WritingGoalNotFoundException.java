package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class WritingGoalNotFoundException extends BaseException {

    public WritingGoalNotFoundException() {
        super(ErrorCode.WRITING_GOAL_NOT_FOUND);
    }
}
