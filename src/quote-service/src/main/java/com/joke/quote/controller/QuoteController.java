package com.joke.quote.controller;

import com.joke.common.model.Quote;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;

@Controller("/api/quote")
public class QuoteController {

    private final List<String> quotesText = List.of(
            "Do what you can, with what you have, where you are.",
            "Life is 10% what happens to us and 90% how we react to it.",
            "The best way to predict the future is to create it."
    );

    @Get
    public HttpResponse<List<Quote>> getQuote() {
        List<Quote> quotes = quotesText.stream()
                .map(Quote::new)
                .toList();

        return HttpResponse.ok(quotes);
    }

    @Get("/debug")
    public HttpResponse<String> debugJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String javaHome = System.getProperty("java.home");

        String info = String.format("Java Version: %s\nVendor: %s\nJava Home: %s",
                javaVersion, javaVendor, javaHome);
        return HttpResponse.ok(info);
    }
}