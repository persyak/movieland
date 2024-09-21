package com.ohorodnik.movieland.utils.converter;

import com.ohorodnik.movieland.annotation.StringToEnumConverter;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import org.springframework.core.convert.converter.Converter;

@StringToEnumConverter
public class StringToEnumRatingOrderConverter implements Converter<String, RatingSortingOrder> {

    @Override
    public RatingSortingOrder convert(String ratingSortingOrder) {
        try {
            return RatingSortingOrder.valueOf(ratingSortingOrder.toLowerCase());
        } catch (IllegalArgumentException e){
            throw new RuntimeException("RatingSortingOrder should be DESC/desc");
        }
    }
}
