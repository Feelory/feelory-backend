package com.feelory.feelory_backend.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthProvider {
    LOCAL("기본 이메일 로그인"),
    KAKAO("카카오 소셜 로그인");

    private final String description;
}

