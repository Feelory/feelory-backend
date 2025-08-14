package com.feelory.feelory_backend.global.exception.dto.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // E0XX : AUTH(인증/토큰)
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "E000", "인증에 실패하였습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "E001", "접근 권한이 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "리프레시 토큰이 존재하지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "E003", "리프레시 토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.NOT_FOUND, "E004", "토큰 정보가 유효하지 않습니다."),
    ADMIN_ACCESS_DENIED(HttpStatus.NOT_FOUND, "E005", "Admin 권한이 없습니다."),
    USER_ACCESS_DENIED(HttpStatus.NOT_FOUND, "E006", "User 또는 Admin 권한이 없습니다."),
    ILLEGAL_USER_TYPE(HttpStatus.NOT_FOUND, "E007", "잘못된 유저 타입입니다."),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "E008", "로그인한 유저의 아이디 추출 시 오류가 발생했습니다."),

    // E1XX : USER(사용자)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E100", "해당 유저를 찾을 수 없습니다."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "E101", "유효하지 않은 전화번호 형식입니다."),

    // E2XX : WORD(단어)
    WORD_NOT_FOUND(HttpStatus.NOT_FOUND, "E200", "단어를 찾을 수 없습니다."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "E201", "중복된 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "E202", "활성화 된 카테고리를 찾지 못했습니다."),
    DUPLICATE_WORD_NAME(HttpStatus.CONFLICT, "E203", "중복된 단어 이름입니다."),
    DAILY_WORD_NOT_FOUND(HttpStatus.NOT_FOUND, "E204", "오늘의 단어를 찾을 수 없습니다."),
    INVALID_TOPIC_DATE(HttpStatus.BAD_REQUEST, "E205", "현재 날짜 이전의 날짜를 지정할 수 없습니다."),
    WORD_ALREADY_USED(HttpStatus.BAD_REQUEST, "E206", "이미 등록된 단어입니다."),

    // E3XX : WRITING(글)
    WRITING_NOT_FOUND(HttpStatus.NOT_FOUND, "E300", "글을 찾지 못했습니다."),
    WRITING_GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "E301", "글쓰기 목표를 찾지 못했습니다."),
    DUPLICATE_WRITING_GOAL_NAME(HttpStatus.CONFLICT, "E302", "중복된 글쓰기 목표 이름입니다."),
    DAILY_WORD_CONFLICT(HttpStatus.CONFLICT, "E303", "해당 단어로 오늘의 글을 이미 작성했습니다."),
    WRITING_GOAL_CONFLICT(HttpStatus.CONFLICT, "E304", "해당 목표로 오늘의 글을 이미 작성했습니다."),

    // E4XX : LIKE, BOOKMARK (좋아요, 북마크)

    // E5XX : FEEDBACK(피드백)

    // E6XX : FILE (파일)
    FILE_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "E900", "파일이 제공되지 않았습니다."),
    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST, "E901", "유효하지 않은 파일 이름입니다."),
    FILE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E902", "파일 저장에 실패하였습니다."),
    IMAGE_FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "E903", "이미지 파일이 15MB를 초과합니다."),
    UNSUPPORTED_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "E904", "지원하지 않는 이미지 파일 형식입니다."),

    // E9XX : 기타
    DAY_TO_FAR_IN_PAST(HttpStatus.BAD_REQUEST, "994", "날짜는 현재로부터 이전 %개월 까지만 가능합니다."),
    DAY_TO_FAR_IN_FUTURE(HttpStatus.BAD_REQUEST, "995", "날짜는 현재로부터 최후 %개월 까지만 가능합니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "996", "잘못된 날짜 형식입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"E997", "입력값이 유효하지 않습니다."),
    NOT_FOUND_END_POINT(HttpStatus.INTERNAL_SERVER_ERROR, "E998", "요청한 API가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E999", "서버 내부 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}
