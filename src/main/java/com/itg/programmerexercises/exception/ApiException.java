package com.itg.programmerexercises.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    // Mimic Default Spring Boot Error For 404 Consistency
    private final ZonedDateTime timestamp;
    private final HttpStatus status;
    private final String error;
    private final String message;

    public ApiException(ZonedDateTime timestamp, HttpStatus status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status.value();
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
