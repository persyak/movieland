package com.ohorodnik.movieland.utils.converter;

import com.ohorodnik.movieland.annotation.StringToEnumConverter;
import com.ohorodnik.movieland.utils.enums.Currency;
import org.springframework.core.convert.converter.Converter;

@StringToEnumConverter
public class StringToEnumCurrencyConverter implements Converter<String, Currency> {

    @Override
    public Currency convert(String currency) {
        try {
            return Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Currency should be USD/usd or EUR/eur");
        }
    }
}
