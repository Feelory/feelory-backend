package com.feelory.feelory_backend.global.exception.exceptions.common;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import com.feelory.feelory_backend.global.exception.exceptions.FormattableBaseException;

public class DayTooFarInPastException extends FormattableBaseException {
    public DayTooFarInPastException(int month) {
        super(ErrorCode.DAY_TO_FAR_IN_PAST, ErrorCode.DAY_TO_FAR_IN_PAST.formatMessage(month));
    }
}
