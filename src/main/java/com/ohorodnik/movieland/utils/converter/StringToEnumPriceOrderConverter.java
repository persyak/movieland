package com.ohorodnik.movieland.utils.converter;

import com.ohorodnik.movieland.annotation.StringToEnumConverter;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import org.springframework.core.convert.converter.Converter;

@StringToEnumConverter
public class StringToEnumPriceOrderConverter implements Converter<String, PriceSortingOrder> {

    @Override
    public PriceSortingOrder convert(String priceSortingOrder) {
        try {
            return PriceSortingOrder.valueOf(priceSortingOrder.toLowerCase());
        } catch (IllegalArgumentException e){
            throw new RuntimeException("PriceSortingOrder should be ASC/asc or DESC/desc");
        }
    }
}
