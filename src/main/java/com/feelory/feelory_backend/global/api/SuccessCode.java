package com.feelory.feelory_backend.global.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    // AUTH(인증/토큰)

    // USERS(사용자)

    //  WORDS(단어)
    GET_CATEGORY_LIST_SUCCESS("카테고리 목록 조회 완료"),
    REGISTER_CATEGORY_SUCCESS("카테고리 추가 완료"),
    UPDATE_CATEGORY_SUCCESS("카테고리 수정 완료"),
    DELETE_CATEGORY_SUCCESS("카테고리 삭제 완료"),

    // WRITINGS(글)


    // LIKES, BOOKMARKS (좋아요, 북마크)

    // FEFEEDBACKS(피드백)

    // 기타
    DEFAULT_SUCCESS("API 요청 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(String message) {
        this.status = HttpStatus.OK;
        this.code = "SUCCESS";
        this.message = message;
    }
}
