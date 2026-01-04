package com.encurtai.exception;

public class UrlDuplicatedException extends RuntimeException{
    public UrlDuplicatedException(String message) {
        super(message);
    }
}
