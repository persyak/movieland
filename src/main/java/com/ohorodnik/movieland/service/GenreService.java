package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
