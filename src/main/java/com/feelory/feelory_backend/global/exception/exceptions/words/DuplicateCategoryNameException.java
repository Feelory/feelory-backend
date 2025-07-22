package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class DuplicateCategoryNameException extends BaseException {
    public DuplicateCategoryNameException() {
        super(ErrorCode.DUPLICATE_CATEGORY_NAME);
    }
}
