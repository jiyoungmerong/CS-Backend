package com.project.ity.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ;

    private final int statusCode;
    private final String message;

    ErrorCode(int status, String message) {
        this.statusCode = status;
        this.message = message;
    }

}
