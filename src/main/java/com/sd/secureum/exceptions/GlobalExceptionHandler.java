package com.sd.secureum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ApiExceptionFormat buildResponseEntity(HttpStatus status, String message) {
        ApiExceptionFormat apiException = new ApiExceptionFormat();
        apiException.setStatus(status.value());
        apiException.setMessage(message);
        apiException.setTimestamp(LocalDateTime.now());
        return apiException;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UmConflictException.class)
    public ApiExceptionFormat handleUmConflictException(UmConflictException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }
}
