package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.entity.Genre;

import java.util.List;

public interface GenreCache {

    List<Genre> findAll();

    List<Genre> findByIdList(List<Integer> ids);
}
