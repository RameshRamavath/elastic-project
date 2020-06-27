package com.nosql.elastic;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AppError {
    private HttpStatus status;
    private String message;
    private List<ErrorDetails> errors;

    public AppError(HttpStatus status, String message, List<ErrorDetails> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public AppError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetails> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetails> errors) {
        this.errors = errors;
    }
}
