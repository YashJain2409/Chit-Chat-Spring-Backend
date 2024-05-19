package com.chatapp.backend.exception;


import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
