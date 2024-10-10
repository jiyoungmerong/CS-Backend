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

    // Oauth2
    KAKAO_REQUEST_FAIL(500, "카카오 API 요청 실패"),

    // 회원가입 시 중복 체크
    ID_ALREADY_REGISTERED(409, "이미 가입된 아이디입니다."),
    NUMBER_ALREADY_REGISTERED(409, "이미 가입된 번호입니다."),
    NICKNAME_ALREADY_REGISTERED(409, "이미 가입된 닉네임입니다."),
    USER_NOT_FOUND(404, "해당 사용자가 존재하지 않습니다."),

    // CS Topic
    NOT_REGISTER_CS_TOPIC(404, "존재하지 않는 CS 주제입니다."),

    // CS Answer
    ALREADY_EXIST_CS_ANSWER(409, "이미 작성한 답변이 존재합니다."),
    NOT_EXISTS_RANKING(404, "랭킹이 존재하지 않습니다."),

    // like
    ALREADY_EXISTS_LIKE(409, "이미 공감을 누른 답변입니다."),
    NOT_EXISTS_LIKE(404, "공감을 찾을 수 없습니다."),

    //Skill
    NOT_EXIT_SKILL(404, "스킬 목록이 존재하지 않습니다."),
    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int status, String message) {
        this.statusCode = status;
        this.message = message;
    }

}
