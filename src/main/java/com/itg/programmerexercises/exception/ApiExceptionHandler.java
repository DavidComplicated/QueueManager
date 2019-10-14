package com.itg.programmerexercises.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CannotCreateAircraftException.class})
    public ResponseEntity<Object> handleApiACQMException(CannotCreateAircraftException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status,
                e.getMessage(),
                "No message available"
        );
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = {NoAircraftFoundException.class})
    public ResponseEntity<Object> handleApiACQMException(NoAircraftFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status,
                e.getMessage(),
                "No message available"
        );
        return new ResponseEntity<>(exception, status);
    }
}
