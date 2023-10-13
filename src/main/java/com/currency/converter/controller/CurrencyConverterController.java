package com.currency.converter.controller;

import com.currency.converter.entity.Currency;
import com.currency.converter.repository.CurrencyConverterRepository;
import com.currency.converter.service.CurrencyConverterService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

//@RestController
@Controller
//@RequestMapping("/v1")
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService service;
//    private CurrencyConverterRepository repository;

    @GetMapping
    public String index(){
        return "index";
    }

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping("/currency-conversion")
    public ResponseEntity<String> performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") String amount,
            @RequestParam("date") Date date) {

        int maxLength = 255;

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide valid values.");
        }
        if (!isNumeric(amount)) {
            return ResponseEntity.badRequest().body("Invalid input. Amount should contain only numerical values.");
        }

        try {
            String apiUrl = "https://api.apilayer.com/fixer/convert?" +
                    "to=" + toCurrency +
                    "&from=" + fromCurrency +
                    "&amount=" + amount+
                    "&date=" + date;

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("apikey", "8JpJ9HZ4x6C2mvmAHTIVtMVOFUF1wLZT")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                String truncatedResponse = responseBody.length() > maxLength ? responseBody.substring(0, maxLength) : responseBody;

//                BigDecimal conversionRate = extractConversionRate(responseBody);
//                BigDecimal convertedAmount = amount.multiply(conversionRate);

                Currency currencySave = new Currency();
                currencySave.setFromCurrency(fromCurrency);
                currencySave.setToCurrency(toCurrency);
//                BigDecimal amountValue = new BigDecimal(String.valueOf(amount));
                currencySave.setAmount(amount);
                currencySave.setDate(date);
                currencySave.setConversionResult(truncatedResponse);

                service.saveData(currencySave);
                return ResponseEntity.ok("Conversion Result: " + responseBody);
            } else {
                if (response.code() == 400) {
                    return ResponseEntity.badRequest().body("Invalid conversion request. Please check your input.");
                } else if (response.code() == 404) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Request failed: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while making the request: " + e.getMessage());
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?"); // This regular expression allows for decimal numbers as well.
    }

    @GetMapping("/locale")
    public String changeLocale(@RequestParam String language,HttpServletRequest request, HttpServletResponse response){
        Locale locale = new Locale(language);
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, locale);
        System.out.println("Language selected"+language);
        return "index";
    }

        @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("pageTitle","Currency Converter Application");

        Locale currentLocale = request.getLocale();
        String countryCode = currentLocale.getCountry();
        String countryName = currentLocale.getDisplayCountry();

        String langCode = currentLocale.getLanguage();
        String langName = currentLocale.getDisplayLanguage();

        System.out.println(countryCode + ": "+ countryName);
        System.out.println(langCode + ": " + langName);

        System.out.println("----------------");
        String[] language = Locale.getISOLanguages();

        for (String lang : language){
            Locale locale = new Locale(lang);
//            System.out.println(lang +":"+locale.getDisplayLanguage());
        }

        return "index";
    }

//    @GetMapping("/home")
//    public String home(){
//        String escapedStr = "\\u0905\\u092d\\u0940 \\u0938\\u092e\\u092f \\u0939\\u0948 \\u091c\\u0928\\u0924\\u093e";
//        String hindiText = StringEscapeUtils.unescapeJava(escapedStr);
//        System.out.println(hindiText);
//        return hindiText;
////        return "home";
//    }

    @GetMapping("/conversion-history")
    public String showConversionHistory(Model model){
        List<Currency> currencyList = service.getAllData();
        model.addAttribute("conversionHistory",currencyList);
        return "history";
    }
}
