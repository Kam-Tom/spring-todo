package com.zawadzkia.springtodo.exception;

public class ResourceNotEmptyException extends RuntimeException {
    public ResourceNotEmptyException(String message) {
        super(message);
    }
}