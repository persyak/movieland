package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.MovieDetailsDto;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Cache
public class MovieCacheImpl implements MovieCache {

    private final ConcurrentHashMap<Integer, SoftReference<MovieDetailsDto>> map =
            new ConcurrentHashMap<>();

    @Override
    public MovieDetailsDto get(Integer movieId) {
        MovieDetailsDto movieDetailsDto = map.get(movieId).get();
        if (movieDetailsDto == null) {
            map.clear();
        }
        return movieDetailsDto;
    }

    @Override
    public Optional<SoftReference<MovieDetailsDto>> put(Integer movieId, MovieDetailsDto editMovieDto) {
        return Optional.ofNullable(map.put(movieId, new SoftReference<>(editMovieDto)));
    }

    @Override
    public boolean contains(Integer movieId) {
        return map.containsKey(movieId);
    }

    @Override
    public MovieDetailsDto remove(Integer movieId) {
        return map.remove(movieId).get();
    }
}
