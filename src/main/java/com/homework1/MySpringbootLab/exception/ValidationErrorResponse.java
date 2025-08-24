package com.homework1.MySpringbootLab.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data @Builder
@AllArgsConstructor
public class ValidationErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
