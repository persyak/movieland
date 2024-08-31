package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    protected List<Movie> findAll(
            @RequestParam(required = false) Optional<String> rating,
            @RequestParam(required = false) Optional<String> price) {
        return movieService.findAll(rating, price);
    }

    @GetMapping("/random")
    protected Iterable<Movie> findRandomThree() {
        return movieService.findRandomThree();
    }

    @GetMapping("/genre/{genreId}")
    protected Iterable<Movie> findByGenreId(@PathVariable int genreId) {
        return movieService.findByGenreId(genreId);
    }

}
