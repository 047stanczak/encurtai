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

    @ExceptionHandler(UrlByIdNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUrlByIdNotFound(UrlByIdNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ApiResponse.error(404, ex.getMessage()));
    }

    @ExceptionHandler(UrlAccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUrlAccessDenied(UrlAccessDeniedException ex) {
        return ResponseEntity
                .status(403)
                .body(ApiResponse.error(403, ex.getMessage()));
    }

    @ExceptionHandler(UrlByHashNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUrlByHashNotFound(UrlByHashNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ApiResponse.error(404, ex.getMessage()));
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidUrl(InvalidUrlException ex) {
        return ResponseEntity
                .status(400)
                .body(ApiResponse.error(400, ex.getMessage()));
    }
}
