package com.feelory.feelory_backend.global.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // E0XX : AUTH(인증/토큰)

    // E1XX : USERS(사용자)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E100", "해당 유저를 찾을 수 없습니다."),

    // E2XX : WORDS(단어)
    WORD_NOT_FOUND(HttpStatus.NOT_FOUND, "E200", "단어를 찾을 수 없습니다."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "E201", "중복된 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "E202", "활성화 된 카테고리를 찾지 못했습니다"),
    DUPLICATE_WORD_NAME(HttpStatus.CONFLICT, "E203", "중복된 단어 이름입니다."),
    DAILY_WORD_NOT_FOUND(HttpStatus.NOT_FOUND, "E204", "오늘의 단어를 찾을 수 없습니다."),
    INVALID_TOPIC_DATE(HttpStatus.BAD_REQUEST, "E205", "현재 날짜 이전의 날짜를 지정할 수 없습니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "E205", "잘못된 날짜 형식입니다."),

    // E3XX : WRITINGS(글)

    // E4XX : LIKES, BOOKMARKS (좋아요, 북마크)

    // E5XX : FEEDBACKS(피드백)

    // E9XX : 기타
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"E997", "입력값이 유효하지 않습니다."),
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
