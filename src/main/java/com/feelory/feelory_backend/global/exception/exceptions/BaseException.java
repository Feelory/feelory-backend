package com.feelory.feelory_backend.global.exception.exceptions;

import com.feelory.feelory_backend.global.exception.dto.model.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
  private final ErrorCode errorCode;

  protected BaseException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
