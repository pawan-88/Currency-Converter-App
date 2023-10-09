package com.currency.converter.controller;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
//@RequestMapping("/v1")
public class CurrencyConverterController {


    public String index(){
        return "index";
    }

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping("/currency-conversion")
    public ResponseEntity<String> performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") Double amount) {

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide valid values.");
        }

        try {
            String apiUrl = "https://api.apilayer.com/fixer/convert?" +
                    "to=" + toCurrency +
                    "&from=" + fromCurrency +
                    "&amount=" + amount;

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("apikey", "8JpJ9HZ4x6C2mvmAHTIVtMVOFUF1wLZT")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
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
}
