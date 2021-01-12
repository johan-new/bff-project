package com.yrgo.bff.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {HandleBadRequestException.class})
    public ResponseEntity<Object> handleBadRequestsException(HandleBadRequestException e, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), request.getDescription(false));
        //ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("ECT")));

        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler(value = {HandleNotFoundExceptions.class})
    public ResponseEntity<Object> handleNotFoundExceptions(HandleNotFoundExceptions e, WebRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(value = {HandleUnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(HandleUnauthorizedException e, WebRequest request) {
        HttpStatus unauth = HttpStatus.UNAUTHORIZED;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, unauth);
    }

    @ExceptionHandler(value = {HandleInternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerException (HandleInternalServerErrorException e, WebRequest request) {
        HttpStatus exception = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, exception);
    }

    @ExceptionHandler(value = {ForbiddenErrorException.class})
    public ResponseEntity<Object> handleForbiddenErrorException (ForbiddenErrorException e, WebRequest request) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, forbidden);
    }

    @ExceptionHandler(value = {HandleATeapotException.class})
    public ResponseEntity<Object> HandleATeapotException(HandleATeapotException e, WebRequest request) {
        HttpStatus teapot = HttpStatus.I_AM_A_TEAPOT;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.I_AM_A_TEAPOT.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, teapot);
    }
}