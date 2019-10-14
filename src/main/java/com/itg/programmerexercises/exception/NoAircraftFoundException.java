package com.itg.programmerexercises.exception;

public class NoAircraftFoundException extends RuntimeException {
    public NoAircraftFoundException(String message) {
        super(message);
    }
}
