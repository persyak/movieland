package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    protected Iterable<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/random")
    protected Iterable<Movie> getThreeRandomMovies() {
        return movieService.getThreeRandomMovies();
    }

    @GetMapping("/genre/{genreId}")
    protected Iterable<Movie> getMoviesByGenre(@PathVariable int genreId) {
        return movieService.getMoviesByGenre(genreId);
    }

}
