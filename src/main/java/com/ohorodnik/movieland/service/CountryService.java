package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> find(List<Integer> id);
}
