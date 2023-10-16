package com.currency.converter.controller;

import com.currency.converter.entity.Currency;
import com.currency.converter.exeption.*;
import com.currency.converter.service.CurrencyConverterService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

//        int maxLength = 255;

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            throw new InvalidInputException("Invalid input. Please provide valid values.");
        }
        if (!isNumeric(amount)) {
            throw new InvalidInputException("Invalid input. Amount should contain only numerical values.");
        }
        if (date == null) {
            date = LocalDate.now(); // Set the default value to the current date
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

                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonResponse = jsonParser.parseMap(responseBody);
                BigDecimal convertedAmount = new BigDecimal(jsonResponse.get("result").toString());
                BigDecimal todayRate = new BigDecimal(((Map<String, Object>)jsonResponse.get("info")).get("rate").toString());
//                BigDecimal conversionRate = extractConversionRate(responseBody);
//                BigDecimal convertedAmount = amount.multiply(conversionRate);

                Currency currencySave = new Currency();
                currencySave.setFromCurrency(fromCurrency);
                currencySave.setToCurrency(toCurrency);
                currencySave.setAmount(amount);
                currencySave.setDate(date);
                currencySave.setConversionResult(convertedAmount.toString());
                currencySave.setTodayRate(todayRate.toString());

                service.saveData(currencySave);
                return ResponseEntity.ok("Conversion Result: " + currencySave);
            } else {
                if (response.code() == 400) {
                    throw new InvalidConversionRequestException("Invalid conversion request. Please check your input.");
                } else if (response.code() == 404) {
                    throw new CurrencyNotFoundException("Currency not found.");
                } else {
                    throw new RequestFailedException("Request failed: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            throw new RequestFailedException("Error while making the request: " + e.getMessage());        }
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
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

    @GetMapping("/conversion-history")
    public String showConversionHistory(Model model, @RequestParam(name = "page", defaultValue = "1") int page){

        int pageSize = 10; // Number of items per page
        List<Currency> currencyList = service.getAllData();

        int totalItems = currencyList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (page < 1 || page > totalPages) {
            throw new PageNotFoundException("Requested page not found.");
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        List<Currency> paginatedConversions = currencyList.subList(startIndex, endIndex);

        Collections.reverse(currencyList);
        model.addAttribute("paginatedConversions", paginatedConversions);
        model.addAttribute("totalPages", totalPages);
        return "history";
    }
}
