package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.MovieDetailsDto;

import java.util.concurrent.ConcurrentHashMap;

@Cache
public class MovieCacheImpl implements MovieCache {

    private final ConcurrentHashMap<Integer, MovieDetailsDto> map = new ConcurrentHashMap<>();

    @Override
    public MovieDetailsDto get(Integer movieId) {
        return map.get(movieId);
    }

    @Override
    public MovieDetailsDto put(Integer movieId, MovieDetailsDto editMovieDto) {
        return map.put(movieId, editMovieDto);
    }

    @Override
    public boolean contains(Integer movieId) {
        return map.containsKey(movieId);
    }

    @Override
    public MovieDetailsDto remove(Integer movieId) {
        return map.remove(movieId);
    }
}
