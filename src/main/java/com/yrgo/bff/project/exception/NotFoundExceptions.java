package com.yrgo.bff.project.exception;

public class NotFoundExceptions extends RuntimeException {


    public NotFoundExceptions(String message) {
        super(message);
    }

    public NotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
