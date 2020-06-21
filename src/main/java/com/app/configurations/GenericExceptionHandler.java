package com.app.configurations;

import com.app.exceptions.BookNotFoundException;
import com.app.exceptions.MediaException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new EndPointError(HttpStatus.BAD_REQUEST, error));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new EndPointError(HttpStatus.BAD_REQUEST, error));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponseEntity(new EndPointError(HttpStatus.BAD_REQUEST, errors.toString()));
    }

    private ResponseEntity<Object> buildResponseEntity(EndPointError endPointError) {
        return new ResponseEntity<>(endPointError, endPointError.getStatus());
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFound(
            BookNotFoundException ex) {
        EndPointError endPointError = new EndPointError(NOT_FOUND);
        endPointError.setMessage(ex.getMessage());
        return buildResponseEntity(endPointError);
    }

    @ExceptionHandler(MediaException.class)
    protected ResponseEntity<Object> handleMediaException(
            MediaException ex) {
        EndPointError endPointError = new EndPointError(NOT_FOUND);
        endPointError.setMessage(ex.getMessage());
        return buildResponseEntity(endPointError);
    }
}
