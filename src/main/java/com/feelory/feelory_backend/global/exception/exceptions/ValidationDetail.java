package com.feelory.feelory_backend.global.exception.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationDetail {
    private String field;
    private String message;
}
