package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies(Optional<String> rating, Optional<String> price);

    List<Movie> getThreeRandomMovies();

    List<Movie> getMoviesByGenre(int genreId);
}
