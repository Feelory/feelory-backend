package com.feelory.feelory_backend.global.exception.exceptions.auth;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;

public class AdminAccessDeniedException extends BaseException {
    public AdminAccessDeniedException() {
        super(ErrorCode.ADMIN_ACCESS_DENIED);
    }
}
