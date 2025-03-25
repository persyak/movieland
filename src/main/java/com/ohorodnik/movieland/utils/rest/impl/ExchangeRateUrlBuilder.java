package com.ohorodnik.movieland.utils.rest.impl;

import com.ohorodnik.movieland.utils.rest.UrlBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateUrlBuilder implements UrlBuilder {

    @Value("${caching.currency.exchangeRatesUrl}")
    private String BASEURL;

    private static final String VALCODEPARAM = "valcode";
    private static final String DATEPARAM = "date";
    private static final String JSONSUFFIX = "json";
    private static final String QUESTIONMARK = "?";
    private static final String EQUALSMARK = "=";
    private static final String ANDMARK = "&";

    //TODO: think how to replace method to avoid unknown number of args
    @Override
    public String build(String... args) {
        StringBuilder requestStringBuilder = new StringBuilder(BASEURL);
        requestStringBuilder
                .append(QUESTIONMARK)
                .append(VALCODEPARAM)
                .append(EQUALSMARK)
                .append(args[0])
                .append(ANDMARK)
                .append(DATEPARAM)
                .append(EQUALSMARK)
                .append(args[1])
                .append(ANDMARK)
                .append(JSONSUFFIX);
        return requestStringBuilder.toString();
    }
}
