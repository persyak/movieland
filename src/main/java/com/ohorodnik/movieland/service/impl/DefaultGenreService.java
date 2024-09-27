package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreCache genreCache;

    @Override
    public List<GenreDto> findAll() {
        return genreCache.findAll();
    }
}
