package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> getAllMovies();

    List<Integer> getMovieIdsByGenreId(int genreId);

    List<Movie> getMoviesByMovieIds(List<Integer> movieIdsList);
}
