package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;

public interface MovieService {
    Iterable<Movie> getAllMovies();

    Iterable<Movie> getThreeRandomMovies();

    Iterable<Movie> getMoviesByGenre(int genreId);
}
