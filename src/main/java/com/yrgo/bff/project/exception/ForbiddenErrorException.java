package com.yrgo.bff.project.exception;

public class ForbiddenErrorException extends RuntimeException {

    public ForbiddenErrorException(String message) {
        super(message);
    }

    public ForbiddenErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
