package com.ohorodnik.movieland.utils.rest.impl;

import com.ohorodnik.movieland.utils.rest.UrlBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateUrlBuilder implements UrlBuilder {

    private static final String BASEURL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange";
    private static final String VALCODEPARAM = "valcode";
    private static final String DATEPARAM = "date";
    private static final String JSONSUFFIX = "json";
    private static final String QUESTIONMARK = "?";
    private static final String EQUALSMARK = "=";
    private static final String ANDMARK = "&";


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
        String request = requestStringBuilder.toString();
        return request;
    }
}
