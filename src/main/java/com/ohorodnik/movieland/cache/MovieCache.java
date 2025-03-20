package com.ohorodnik.movieland.cache;

import com.ohorodnik.movieland.dto.MovieDetailsDto;

public interface MovieCache {

    MovieDetailsDto get(Integer movieId);

    void put(Integer id, MovieDetailsDto editMovieDto);
}