package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.CurrencyExchangeRateCache;
import com.ohorodnik.movieland.dto.RateDetailsDto;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.rest.UrlBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Cache
@RequiredArgsConstructor
public class CurrencyExchangeRateCacheImpl implements CurrencyExchangeRateCache {

    private volatile Map<Currency, RateDetailsDto> rates;
    private final WebClient.Builder webClientBuilder;
    @Qualifier("ExchangeRateUrlBuilder")
    private final UrlBuilder urlBuilder;

    @Override
    public RateDetailsDto getRate(Currency currency) {
        //TODO: prepare some fallback or something to do when no rates are available
        if (rates.isEmpty() || !rates.get(Currency.USD).getExchangedate().equals(new SimpleDateFormat("dd.MM.yyyy").format(new Date()))) {
            rates = provideRates();
            if (rates.isEmpty() || !rates.get(Currency.USD).getExchangedate().equals(new SimpleDateFormat("dd.MM.yyyy").format(new Date()))) {
                throw new RuntimeException("rates are not updated");
            }
        }
        return rates.get(currency);
    }

    @PostConstruct
    private void initCache() {
        rates = provideRates();
    }

    //It will run at 9:01 AM according to system time, where application is running. Usually rate is updated at 9 AM.
    @Scheduled(cron = "1 9 * * * ?")
    private void updateCache() {
        rates = provideRates();
    }

    private Map<Currency, RateDetailsDto> provideRates() {
        Map<Currency, RateDetailsDto> currencyMap = new HashMap<>();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        RateDetailsDto usdRateDetailsDto = getCurrencyRate(provideExchangeRequest(Currency.USD.toString(), date));
        RateDetailsDto eurRateDetailsDto = getCurrencyRate(provideExchangeRequest(Currency.EUR.toString(), date));

        currencyMap.put(Currency.USD, usdRateDetailsDto);
        currencyMap.put(Currency.EUR, eurRateDetailsDto);

        return currencyMap;
    }

    private String provideExchangeRequest(String currency, String date) {
        return urlBuilder.build(currency, date);
    }

    private RateDetailsDto getCurrencyRate(String requestUrl) {
        return webClientBuilder
                .build().get()
                .uri(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(RateDetailsDto.class))
                .blockFirst();
    }
}
