package com.feelory.feelory_backend.global.exception.exceptions.auth;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class UserAccessDeniedException extends BaseException {
    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
