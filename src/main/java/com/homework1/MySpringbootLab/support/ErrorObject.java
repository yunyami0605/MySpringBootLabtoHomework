package com.homework1.MySpringbootLab.support;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorObject {
    private final String message;
    private HttpStatus httpStatus;
}
