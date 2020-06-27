package com.nosql.elastic;

import org.springframework.http.HttpStatus;

public class RecordNotFound extends RuntimeException {

    private HttpStatus status;
    private String message;

    public RecordNotFound(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
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
}
