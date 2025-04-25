package com.example.SpringBookstore.exceptionHandling;

import com.example.SpringBookstore.exceptionHandling.exception.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException runtimeException) {
        ErrorDetail errorDetail = new ErrorDetail(INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR", runtimeException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException) {
        ErrorDetail errorDetail = new ErrorDetail(NOT_FOUND.value(), "ENTITY NOT FOUND", entityNotFoundException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestExceptionHandler(BadRequestException badRequestException) {
        ErrorDetail errorDetail = new ErrorDetail(BAD_REQUEST.value(), "BAD REQUEST EXCEPTION", badRequestException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String property = getProperty(error);
            String message = error.getDefaultMessage();
            errors.put(property, message);
        });

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    private String getProperty(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            return ((FieldError) objectError).getField();
        }

        return objectError.getObjectName();
    }
}
