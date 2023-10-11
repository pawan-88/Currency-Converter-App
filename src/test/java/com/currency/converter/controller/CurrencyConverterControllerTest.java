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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterControllerTest {

    @Mock
    private CurrencyConverterRepository mockRepository;

    @InjectMocks
    private CurrencyConverterController currencyConverterControllerUnderTest;

    @Test
    void testIndex1() {
        // Setup
        // Run the test
        final String result = currencyConverterControllerUnderTest.index();

        // Verify the results
        assertEquals("index", result);
    }

    @Test
    void testIndex2() {
        // Setup
        final Model model = new ConcurrentModel();
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Run the test
        final String result = currencyConverterControllerUnderTest.index(model, request);

        // Verify the results
        assertEquals("index", result);
    }

    @Test
    void testPerformCurrencyConversion() throws IOException {
        // Setup
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("body", HttpStatus.OK);

        // Run the test
        final ResponseEntity<String> result = currencyConverterControllerUnderTest.performCurrencyConversion(
                "fromCurrency", "toCurrency", new BigDecimal("0.00"), Date.valueOf(LocalDate.of(2020, 1, 1)));

        // Verify the results
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(mockRepository).save(any(Currency.class));
    }

    @Test
    void testChangeLocale() {
        // Setup
        // Run the test
        final String result = currencyConverterControllerUnderTest.changeLocale("language");

        // Verify the results
        assertEquals("index", result);
    }
}
