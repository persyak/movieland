package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;

public interface MovieRepository {
    Iterable<Movie> getAllMovies();

    List<Integer> getMovieIdsByGenreId(int genreId);

    Iterable<Movie> getMoviesByMovieIds(List<Integer> movieIdsList);
}
