package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.dto.RateDetailsDto;
import com.ohorodnik.movieland.utils.enums.Currency;

public interface CurrencyExchangeRateCache {

    RateDetailsDto getRate(Currency currency);
}
