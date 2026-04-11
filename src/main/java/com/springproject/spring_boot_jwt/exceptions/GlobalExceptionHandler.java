package com.springproject.spring_boot_jwt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex){
        return ResponseEntity.status(401).body(Map.of(
                "error:",ex.getMessage()
        ));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex){
        return ResponseEntity.status(404).body(Map.of(
                "error: ",ex.getMessage()
        ));
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> badRequest(DuplicateException ex){
        return ResponseEntity.status(400).body(Map.of(
                "error",ex.getMessage()
        ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationError(
            MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
