package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class InvalidTopicDateException extends BaseException {
    public InvalidTopicDateException() {
        super(ErrorCode.INVALID_TOPIC_DATE);
    }
}
