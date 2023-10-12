package com.currency.converter.configuration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InternationlizationTest {

    private Internationlization internationlizationUnderTest;

    @BeforeEach
    void setUp() {
        internationlizationUnderTest = new Internationlization();
    }

    @Test
    public void testLocaleResolverBean() {
        // Create a new instance of the CookieLocaleResolver
        LocaleResolver localeResolver = createLocaleResolver();

        // Ensure that the bean is an instance of CookieLocaleResolver
        assertEquals(CookieLocaleResolver.class, localeResolver.getClass());

        // Verify the properties of the CookieLocaleResolver
        CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver) localeResolver;
        assertEquals("local-cookie", cookieLocaleResolver.getCookieName());
        assertEquals(4800, cookieLocaleResolver.getCookieMaxAge());
    }

    private CookieLocaleResolver createLocaleResolver() {
        CookieLocaleResolver sessionLocaleResolver = new CookieLocaleResolver();
        sessionLocaleResolver.setCookieName("local-cookie");
        sessionLocaleResolver.setCookieMaxAge(4800);
        return sessionLocaleResolver;
    }

    @Test
    public void testLocaleChangeInterceptorBean() {
        // Create an instance of the bean
        LocaleChangeInterceptor localeChangeInterceptor = createLocaleChangeInterceptor();
        // Verify that the paramName property is set to "lang"
        assertEquals("lang", localeChangeInterceptor.getParamName());
    }

    private LocaleChangeInterceptor createLocaleChangeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    @Test
    public void testAddInterceptors() {
        // Create an instance of the configuration class
        Internationlization internationlization = new Internationlization();
        // Mock the InterceptorRegistry
        InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);
        // Call the addInterceptors method
        internationlization.addInterceptors(registry);
        // Verify that any instance of LocaleChangeInterceptor was added to the InterceptorRegistry
        verify(registry).addInterceptor(Mockito.any(LocaleChangeInterceptor.class));
    }
}

