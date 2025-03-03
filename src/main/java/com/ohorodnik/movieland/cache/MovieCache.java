package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.dto.MovieDetailsDto;

public interface MovieCache {

    MovieDetailsDto get(Integer movieId);

    MovieDetailsDto put(Integer id, MovieDetailsDto editMovieDto);

    boolean  contains(Integer id);

    MovieDetailsDto remove(Integer id);
}