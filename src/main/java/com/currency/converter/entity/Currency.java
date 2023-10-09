//package com.currency.converter.entity;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "currency_converter_data")
//public class Currency {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String fromCurrency;
//    private String toCurrency;
//    private Double amount;
//    private String conversionResult;
//
//    public Currency() {
//    }
//
//    public Currency(Long id, String fromCurrency, String toCurrency, Double amount, String conversionResult) {
//        this.id = id;
//        this.fromCurrency = fromCurrency;
//        this.toCurrency = toCurrency;
//        this.amount = amount;
//        this.conversionResult = conversionResult;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFromCurrency() {
//        return fromCurrency;
//    }
//
//    public void setFromCurrency(String fromCurrency) {
//        this.fromCurrency = fromCurrency;
//    }
//
//    public String getToCurrency() {
//        return toCurrency;
//    }
//
//    public void setToCurrency(String toCurrency) {
//        this.toCurrency = toCurrency;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public String getConversionResult() {
//        return conversionResult;
//    }
//
//    public void setConversionResult(String conversionResult) {
//        this.conversionResult = conversionResult;
//    }
//}
