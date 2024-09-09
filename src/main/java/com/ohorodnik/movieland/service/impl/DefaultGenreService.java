package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreCache genreCache;

    @Override
    public List<Genre> findAll() {
        return genreCache.findAll();
    }

    @Override
    public Optional<Genre> findById(int id) {
        return genreCache.findById(id);
    }
}
