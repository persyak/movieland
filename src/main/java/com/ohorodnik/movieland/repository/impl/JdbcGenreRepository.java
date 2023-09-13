package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    @Override
    public Iterable<Genre> getAllGenres() {
        return null;
    }
}
