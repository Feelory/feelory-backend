package com.feelory.feelory_backend.users.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ANONYMOUS("익명 사용자"),
    USER("사용자"),
    ADMIN("관리자");

    private final String description;

    public int getLevel() {
        return this.ordinal();
    }
}