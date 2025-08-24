package com.homework1.MySpringbootLab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BusinessException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public BusinessException(String message) {
        this(message, HttpStatus.EXPECTATION_FAILED);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}