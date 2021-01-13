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

    @ExceptionHandler(value = {NotFoundExceptions.class})
    public ResponseEntity<Object> handleNotFoundExceptions(NotFoundExceptions e, WebRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, WebRequest request) {
        HttpStatus unauth = HttpStatus.UNAUTHORIZED;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, unauth);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerException (InternalServerErrorException e, WebRequest request) {
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

    @ExceptionHandler(value = {TeapotException.class})
    public ResponseEntity<Object> HandleATeapotException(TeapotException e, WebRequest request) {
        HttpStatus teapot = HttpStatus.I_AM_A_TEAPOT;
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.I_AM_A_TEAPOT.value(), new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, teapot);
    }
}