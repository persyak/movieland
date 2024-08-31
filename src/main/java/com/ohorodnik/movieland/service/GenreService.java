package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Genre;

import java.util.Optional;

public interface GenreService {
    Iterable<Genre> findAll();

    Optional<Genre> findById(int id);
}
