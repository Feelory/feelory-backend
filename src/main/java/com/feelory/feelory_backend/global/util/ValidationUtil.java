package com.feelory.feelory_backend.global.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    public boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
