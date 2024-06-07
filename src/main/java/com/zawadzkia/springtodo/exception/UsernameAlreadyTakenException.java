package com.zawadzkia.springtodo.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException() {
        super("Username is already taken.");
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}