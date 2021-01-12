package com.yrgo.bff.project.exception;

public class HandleBadRequestException extends RuntimeException {

    public HandleBadRequestException(String message) {
        super(message);
    }

    public HandleBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
