package com.project.ity.global.exception.exceptions;

import com.project.ity.global.exception.ErrorCode;

public class JwtAuthException extends AppServiceException {
    public JwtAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
    public JwtAuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
