package com.feelory.feelory_backend.global.exception.exceptions.feedback;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class ExternalApiConnectionException extends BaseException {
    public ExternalApiConnectionException() {
        super(ErrorCode.EXTERNAL_API_CONNECTION_FAILED);
    }
}
