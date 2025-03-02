package com.example.BookStoreSpring.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.InputMismatchException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ErrorDetail error = new ErrorDetail(NOT_FOUND.value(), "NOT FOUND", entityNotFoundException.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<?> handleInputMismatchException(InputMismatchException inputMismatchException) {
        ErrorDetail error = new ErrorDetail(BAD_REQUEST.value(), "INPUT MISMATCH", inputMismatchException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}
