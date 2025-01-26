package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.AddCountryDto;
import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toCountryDto(Country country);

    List<CountryDto> toCountryDtoList(List<Country> countries);

    Country toCountry(AddCountryDto addCountryDto);

    List<Country> toCountry(List<AddCountryDto> countries);
}
