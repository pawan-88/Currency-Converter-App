package com.currency.converter.exeption;

public class RequestFailedException extends RuntimeException{

    public RequestFailedException(String message) {
        super(message);
    }
}
