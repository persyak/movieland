package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.mapper.CountryMapper;
import com.ohorodnik.movieland.repository.CountryRepository;
import com.ohorodnik.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> find(List<Integer> id) {
        return countryMapper.toCountryDtoList(countryRepository.findAllById(id));
    }
}
