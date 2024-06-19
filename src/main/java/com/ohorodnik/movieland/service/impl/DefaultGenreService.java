package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }
}
