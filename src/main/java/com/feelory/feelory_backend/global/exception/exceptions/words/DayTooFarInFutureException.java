package com.feelory.feelory_backend.global.exception.exceptions.words;

import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.global.exception.exceptions.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.FormattableBaseException;

public class DayTooFarInFutureException extends FormattableBaseException {
    public DayTooFarInFutureException(int month) {
        super(ErrorCode.DAY_TO_FAR_IN_FUTURE, ErrorCode.DAY_TO_FAR_IN_FUTURE.formatMessage(month));
    }
}
