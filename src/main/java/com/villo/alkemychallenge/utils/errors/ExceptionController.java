package com.villo.alkemychallenge.utils.errors;

import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.errors.custom.ErrorResponse;
import com.villo.alkemychallenge.utils.errors.custom.IntegrationException;
import com.villo.alkemychallenge.utils.errors.custom.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.IOException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(final HandlerMethodValidationException ex) {
        ex.getAllValidationResults().get(0).getResolvableErrors().get(0).getDefaultMessage();
        var errors = ex.getAllValidationResults()
                .stream()
                .map(parameterValidationResult -> parameterValidationResult.getResolvableErrors()
                        .stream().map(MessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrationException(final IntegrationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final ValidationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getCause().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(final BadCredentialsException ex) {
        return new ResponseEntity<>(new ErrorResponse(Constants.INVALID_CREDENTIALS),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(final IOException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
