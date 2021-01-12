package com.yrgo.bff.project.exception;

public class HandleUnauthorizedException extends RuntimeException {


    public HandleUnauthorizedException(String message) {
        super(message);
    }

    public HandleUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
