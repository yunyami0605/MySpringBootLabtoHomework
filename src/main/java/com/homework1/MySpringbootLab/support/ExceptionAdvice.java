package com.homework1.MySpringbootLab.support;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex) {
        ErrorObject error = ErrorObject.builder()
                .httpStatus(ex.getHttpStatus())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(error.getHttpStatus())
                .body(error);
    }

}
