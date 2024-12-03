package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.CurrencyExchangeRateCache;
import com.ohorodnik.movieland.service.RatesService;
import com.ohorodnik.movieland.utils.enums.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatesServiceImpl implements RatesService {

    private final CurrencyExchangeRateCache currencyExchangeRateCache;

    @Override
    public Double getRate(Currency currency) {
        return currencyExchangeRateCache.getRate(currency).getRate();
    }
}
