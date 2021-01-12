package com.yrgo.bff.project.exception;

public class HandleInternalServerErrorException extends RuntimeException {

    public HandleInternalServerErrorException(String message) {
        super(message);
    }

    public HandleInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
