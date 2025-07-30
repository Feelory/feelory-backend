package com.feelory.feelory_backend.global.exception.exceptions;

import lombok.Getter;

@Getter
public class FormattableBaseException extends BaseException {
  private final String formattedMessage;

  protected FormattableBaseException(ErrorCode errorCode, Object... args) {
    super(errorCode);
    this.formattedMessage = String.format(errorCode.getMessage(), args);
  }

  @Override
  public String getMessage() {
    return formattedMessage;
  }
}
