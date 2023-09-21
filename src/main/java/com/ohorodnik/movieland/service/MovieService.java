package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;

import java.util.Optional;

public interface MovieService {
    Iterable<Movie> getAllMovies(Optional<String> rating, Optional<String> price);

    Iterable<Movie> getThreeRandomMovies();

    Iterable<Movie> getMoviesByGenre(int genreId);
}
