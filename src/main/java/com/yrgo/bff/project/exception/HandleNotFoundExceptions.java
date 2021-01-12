package com.yrgo.bff.project.exception;

public class HandleNotFoundExceptions extends RuntimeException {


    public HandleNotFoundExceptions(String message) {
        super(message);
    }

    public HandleNotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
