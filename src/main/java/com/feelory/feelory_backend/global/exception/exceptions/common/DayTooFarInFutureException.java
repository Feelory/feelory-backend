package com.feelory.feelory_backend.global.exception.exceptions.common;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.FormattableBaseException;

public class DayTooFarInFutureException extends FormattableBaseException {
    public DayTooFarInFutureException(int month) {
        super(ErrorCode.DAY_TO_FAR_IN_FUTURE, ErrorCode.DAY_TO_FAR_IN_FUTURE.formatMessage(month));
    }
}
