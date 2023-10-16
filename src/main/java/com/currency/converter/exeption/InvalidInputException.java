package com.currency.converter.exeption;

import com.currency.converter.controller.CurrencyConverterController;
import org.springframework.beans.factory.annotation.Autowired;

public class InvalidInputException extends RuntimeException{

    @Autowired
    private CurrencyConverterController currencyConverterController;

    public InvalidInputException(String message){
        super(message);

    }
}
