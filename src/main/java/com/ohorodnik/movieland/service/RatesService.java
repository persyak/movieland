package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.utils.enums.Currency;

public interface RatesService {

    Double getRate(Currency currency);
}
