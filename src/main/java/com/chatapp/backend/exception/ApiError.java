package com.chatapp.backend.exception;

import org.springframework.http.HttpStatus;

public class ApiError {
    private String message;
    private int statusCode;
    private String statusName;

    public ApiError(String message, int statusCode, String statusName) {
        this.message = message;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
