package com.feelory.feelory_backend.global.exception.exceptions.user;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class AlreadyDeletedUserException extends BaseException {
    public AlreadyDeletedUserException() {
        super(ErrorCode.ALREADY_DELETED_USER);
    }
}