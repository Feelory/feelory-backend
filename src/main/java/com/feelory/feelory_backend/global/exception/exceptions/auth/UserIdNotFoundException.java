package com.feelory.feelory_backend.global.exception.exceptions.auth;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class UserIdNotFoundException extends BaseException {
    public UserIdNotFoundException() {
        super(ErrorCode.USER_ID_NOT_FOUND);
    }
}
