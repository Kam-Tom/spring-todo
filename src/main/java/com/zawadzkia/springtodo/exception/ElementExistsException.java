package com.zawadzkia.springtodo.exception;

public class ElementExistsException extends RuntimeException {
    public ElementExistsException() {
        super("Element already exists.");
    }

    public ElementExistsException(String element) {
        super("This " + element + " already exists.");
    }

}
