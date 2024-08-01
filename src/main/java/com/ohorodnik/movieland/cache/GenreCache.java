package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.entity.Genre;

import java.util.List;

public interface GenreCache {

    private void updateCache() {
    }

    List<Genre> findAll();
}
