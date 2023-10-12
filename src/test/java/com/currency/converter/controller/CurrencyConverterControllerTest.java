package com.currency.converter.controller;

import com.currency.converter.entity.Currency;
import com.currency.converter.repository.CurrencyConverterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterControllerTest {

    @Mock
    private CurrencyConverterRepository mockRepository;

    @InjectMocks
    private CurrencyConverterController currencyConverterControllerUnderTest;

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
                "USD", "INR", new BigDecimal("5.0"), Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Verify the status code
        assertEquals(expectedResult.getStatusCode(), result.getStatusCode());

        // Check if the expected JSON content is present in the actual content
        assertTrue(result.getBody().contains("\"query\": {"));
        assertTrue(result.getBody().contains("\"result\": 355.6575"));
        verify(mockRepository).save(any(Currency.class));
    }

//    @Test
//    void testChangeLocale() {
//        // Setup
//        // Run the test
////        final String result = currencyConverterControllerUnderTest.changeLocale();
//
//        // Verify the results
//        assertEquals("index", result);
//    }
}
