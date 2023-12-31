package com.currency.converter.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "currency_converter_data")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private String amount;
    private LocalDate date;
    private String conversionResult;
    private String todayRate;


    public Currency(String todayRate) {
        this.todayRate = todayRate;
    }


    public String getTodayRate() {
        return todayRate;
    }

    public void setTodayRate(String todayRate) {
        this.todayRate = todayRate;
    }

    public Currency() {
    }

    public Currency(Long id, String fromCurrency, String toCurrency, String amount, String conversionResult) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.date = date;
        this.conversionResult = conversionResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getConversionResult() {
        return conversionResult;
    }

    public void setConversionResult(String conversionResult) {
        this.conversionResult = conversionResult;
    }

    @Override
    public String toString() {
        return  conversionResult + '\'' +
                ",       TodayRate='" + todayRate + '\'';
    }
}
