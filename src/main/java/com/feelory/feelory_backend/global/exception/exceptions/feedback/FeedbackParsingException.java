package com.feelory.feelory_backend.global.exception.exceptions.feedback;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class FeedbackParsingException extends BaseException {
    public FeedbackParsingException() {
        super(ErrorCode.FEEDBACK_PARSING_FAILED);
    }
}