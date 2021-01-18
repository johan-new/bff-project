package com.yrgo.bff.project.exception;

public class TeapotException extends RuntimeException {

    public TeapotException(String message) {
        super(message);
    }

    public TeapotException(String message, Throwable cause) {
        super(message, cause);
    }
}
