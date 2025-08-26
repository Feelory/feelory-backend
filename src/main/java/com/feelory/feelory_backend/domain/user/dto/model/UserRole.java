package com.feelory.feelory_backend.domain.user.dto.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ANONYMOUS("익명 사용자"),
    DEACTIVATED_USER("비활성화된 사용자"),
    USER("일반 사용자"),
    ADMIN("관리자");

    private final String description;

    public int getLevel() {
        return this.ordinal();
    }
}