package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAllGenres();
}
