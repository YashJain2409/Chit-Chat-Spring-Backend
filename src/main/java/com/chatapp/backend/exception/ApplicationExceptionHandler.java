package com.chatapp.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiError> HandleApplicationException(ApplicationException e){
        ApiError error = new ApiError(e.getMessage(),e.getStatus().value(),e.getStatus().name());
        return new ResponseEntity<>(error,e.getStatus());
    }
}
