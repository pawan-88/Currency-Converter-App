package com.currency.converter.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

class InternationlizationTest {

    private Internationlization internationlizationUnderTest;

    @BeforeEach
    void setUp() {
        internationlizationUnderTest = new Internationlization();
    }

    @Test
    void testLocaleResolver() {
        // Setup
        // Run the test
        final LocaleResolver result = internationlizationUnderTest.localeResolver();

        // Verify the results
    }

    @Test
    void testLocaleChangeInterceptor() {
        // Setup
        // Run the test
        final LocaleChangeInterceptor result = internationlizationUnderTest.localeChangeInterceptor();

        // Verify the results
    }

    @Test
    void testAddInterceptors() {
        // Setup
        final InterceptorRegistry registry = new InterceptorRegistry();

        // Run the test
        internationlizationUnderTest.addInterceptors(registry);

        // Verify the results
    }
}
