package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    protected Iterable<Movie> getAllMovies(
            @RequestParam(required = false) Optional<String> rating,
            @RequestParam(required = false) Optional<String> price) {
        return movieService.getAllMovies(rating, price);
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
