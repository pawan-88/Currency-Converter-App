package com.currency.converter.service;

import com.currency.converter.entity.Currency;
import com.currency.converter.repository.CurrencyConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyConverterService {

    @Autowired
    private CurrencyConverterRepository repository;


    public void saveData(Currency currencySave) {
        repository.save(currencySave);
    }

    public List<Currency> getAllData() {

        return repository.findAll();
    }
}
