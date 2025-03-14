package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.dto.MovieDetailsDto;

import java.lang.ref.SoftReference;
import java.util.Optional;

public interface MovieCache {

    MovieDetailsDto get(Integer movieId);

    Optional<SoftReference<MovieDetailsDto>> put(Integer id, MovieDetailsDto editMovieDto);

    boolean contains(Integer id);

    MovieDetailsDto remove(Integer id);
}