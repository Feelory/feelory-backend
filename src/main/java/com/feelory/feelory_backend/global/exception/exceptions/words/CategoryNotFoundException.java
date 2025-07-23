package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
