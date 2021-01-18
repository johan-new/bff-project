package com.yrgo.bff.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestsException(BadRequestException e, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(NotFoundException e, WebRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerException (InternalServerErrorException e, WebRequest request) {
        HttpStatus exception = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, exception);
    }

    @ExceptionHandler(value = {TeapotException.class})
    public ResponseEntity<Object> HandleTeapotException(TeapotException e, WebRequest request) {
        HttpStatus teapot = HttpStatus.I_AM_A_TEAPOT;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.I_AM_A_TEAPOT.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, teapot);
    }
}