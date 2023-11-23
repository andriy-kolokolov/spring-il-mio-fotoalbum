package com.experis.photoalbum.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errors", errors);
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("success", false);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> responseBody = new HashMap<>();

        String errorMessage = "A data integrity error occurred";
        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            String detailedMessage = ex.getCause().getCause().getMessage();
            if (detailedMessage.contains("UK_r43af9ap4edm43mmtq01oddj6")) {
                errorMessage = "Username already exists. Please choose a different username.";
            }
        }

        responseBody.put("error", errorMessage);
        responseBody.put("status", HttpStatus.CONFLICT.value());
        responseBody.put("success", false);

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }
}
