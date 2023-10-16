package com.currency.converter.exeption;

public class InvalidConversionRequestException extends RuntimeException{

    public InvalidConversionRequestException(String message) {
        super(message);
    }

}
