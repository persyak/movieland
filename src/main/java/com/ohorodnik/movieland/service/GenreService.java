package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();

    Optional<Genre> findById(int id);
}
