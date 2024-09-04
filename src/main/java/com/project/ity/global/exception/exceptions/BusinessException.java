package com.project.ity.global.exception.exceptions;

import com.project.ity.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final int statusCode;
    private final HttpStatus httpStatus;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
    }
}
