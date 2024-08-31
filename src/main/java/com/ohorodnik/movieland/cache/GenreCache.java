package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreCache {

    private void updateCache() {
    }

    List<Genre> findAll();

    Optional<Genre> findById(int id);
}
