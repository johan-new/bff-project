package com.yrgo.bff.project.exception;

public class HandleATeapotException extends RuntimeException {

    public HandleATeapotException(String message) {
        super(message);
    }

    public HandleATeapotException(String message, Throwable cause) {
        super(message, cause);
    }
}
