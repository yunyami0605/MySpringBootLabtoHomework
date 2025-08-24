package com.homework1.MySpringbootLab.exception.advice;

import com.homework1.MySpringbootLab.exception.BusinessException;
import com.homework1.MySpringbootLab.exception.ErrorResponse;
import com.homework1.MySpringbootLab.exception.ValidationErrorResponse;
import com.homework1.MySpringbootLab.support.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse res = ErrorResponse.builder().status(ex.getHttpStatus().value()).message(ex.getMessage()).timestamp(LocalDateTime.now()).build();

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<ErrorResponse>(res, HttpStatusCode.valueOf(ex.getHttpStatus().value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        400,
                        "입력항목 검증 오류",
                        LocalDateTime.now(),
                        errors
                );
        //badRequest() 400
        return ResponseEntity.badRequest().body(response);
    }
}
