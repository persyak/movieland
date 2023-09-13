package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;

public interface GenreRepository {
    Iterable<Genre> getAllGenres();
}
