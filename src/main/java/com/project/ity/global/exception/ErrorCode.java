package com.project.ity.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    HTTP_MESSAGE_NOT_READABLE(400, "요청값을 읽어들이지 못했습니다. 형식을 확인해 주세요."),

    // 인증 - 토큰
    NOT_EXISTS_AUTH_HEADER(401, "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    TOKEN_EXPIRED(401, "해당 token은 만료되었습니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    NO_ACCESS_USER(403, "권한이 없습니다."),

    // 인증 - 로그인 시도
    MISMATCHED_SIGNIN_INFO(400, "잘못된 로그인 정보입니다."),

    // 회원가입 시 중복 체크
    ID_ALREADY_REGISTERED(409, "이미 가입된 아이디입니다."),
    NUMBER_ALREADY_REGISTERED(409, "이미 가입된 번호입니다."),
    NICKNAME_ALREADY_REGISTERED(409, "이미 가입된 닉네임입니다."),
    USER_NOT_FOUND(404, "해당 사용자가 존재하지 않습니다."),

    // CS Topic
    NOT_REGISTER_CS_TOPIC(404, "존재하지 않는 CS 주제입니다."),

    //Skill
    NOT_EXIT_SKILL(404, "존재하지 않는 스킬 목록입니다.")
    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int status, String message) {
        this.statusCode = status;
        this.message = message;
    }

}
