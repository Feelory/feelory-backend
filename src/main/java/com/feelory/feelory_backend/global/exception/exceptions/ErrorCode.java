package com.feelory.feelory_backend.global.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // E0XX : AUTH(인증/토큰)

    // E1XX : USERS(사용자)
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "E100", "해당 유저를 찾을 수 없습니다."),

    // E2XX : WORDS(단어)
    WORDS_NOT_FOUND(HttpStatus.NOT_FOUND, "E200", "단어를 찾을 수 없습니다."),
    EXIST_CATEGORY_NAME(HttpStatus.CONFLICT, "E201", "중복된 카테고리 이름입니다."),

    // E3XX : WRITINGS(글)

    // E4XX : LIKES, BOOKMARKS (좋아요, 북마크)

    // E5XX : FEEDBACKS(피드백)

    // E9XX : 기타
    NOT_FOUND_END_POINT(HttpStatus.INTERNAL_SERVER_ERROR, "E998", "요청한 API가 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E999", "서버 내부 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
