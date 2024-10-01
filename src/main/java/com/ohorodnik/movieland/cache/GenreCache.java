package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.dto.GenreDto;

import java.util.List;

public interface GenreCache {

    List<GenreDto> findAll();
}
