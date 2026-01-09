package com.encurtai.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.encurtai.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmail(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ApiResponse.error(404, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(401)
                .body(ApiResponse.error(401, ex.getMessage()));
    }

    @ExceptionHandler(UrlDuplicatedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUrlDuplicated(UrlDuplicatedException ex) {
        return ResponseEntity
                .status(409)
                .body(ApiResponse.error(409, ex.getMessage()));
    }
}
