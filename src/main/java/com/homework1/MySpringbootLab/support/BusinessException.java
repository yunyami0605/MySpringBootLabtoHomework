package com.homework1.MySpringbootLab.support;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public BusinessException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BusinessException(ErrorCode errorCode, Object... args) {
        this.message = errorCode.formatMessage(args);
        this.httpStatus = errorCode.getHttpStatus();
    }
}
