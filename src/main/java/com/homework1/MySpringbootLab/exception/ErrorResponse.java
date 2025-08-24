package com.homework1.MySpringbootLab.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}
