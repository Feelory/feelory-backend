package com.feelory.feelory_backend.global.exception.exceptions.users;

import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;

public class UsersNotFoundException extends BaseException {

    public UsersNotFoundException() {
        super(ErrorCode.USERS_NOT_FOUND);
    }
}
