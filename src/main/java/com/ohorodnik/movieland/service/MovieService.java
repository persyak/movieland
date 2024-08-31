package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll(Optional<String> rating, Optional<String> price);

    List<Movie> findRandomThree();

    List<Movie> findByGenreId(int genreId);
}
