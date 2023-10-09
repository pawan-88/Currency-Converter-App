package com.currency.converter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyConverterControllerTest {

    private CurrencyConverterController currencyConverterControllerUnderTest;

    @BeforeEach
    void setUp() {
        currencyConverterControllerUnderTest = new CurrencyConverterController();
    }

    @Test
    void testIndex() {
        // Setup
        // Run the test
        final String result = currencyConverterControllerUnderTest.index();

        // Verify the results
        assertEquals("index", result);
    }

    @Test
    void testPerformCurrencyConversion() {
        // Setup
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("body", HttpStatus.OK);

        // Run the test
        final ResponseEntity<String> result = currencyConverterControllerUnderTest.performCurrencyConversion(
                "fromCurrency", "toCurrency", 0.0);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
