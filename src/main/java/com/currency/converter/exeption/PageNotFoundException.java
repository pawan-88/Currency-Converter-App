package com.currency.converter.exeption;

public class PageNotFoundException extends RuntimeException{

    public PageNotFoundException(String message) {
        super(message);
    }
}
