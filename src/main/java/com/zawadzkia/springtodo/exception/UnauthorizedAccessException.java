package com.zawadzkia.springtodo.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("Username is not unauthorized to access this resource.");
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}