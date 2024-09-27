package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    protected List<MovieDto> findAll(MovieRequest movieRequest) {

        if (movieRequest.getRatingSortingOrder() == null && movieRequest.getPriceSortingOrder() == null) {
            return movieService.findAll();
        }
        if (movieRequest.getRatingSortingOrder() != null && movieRequest.getPriceSortingOrder() != null) {
            return movieService.findAllCustomPriceAndRatingSorting(movieRequest.getPriceSortingOrder());
        }
        if (movieRequest.getRatingSortingOrder() != null) {
            return movieService.findAll(movieRequest.getRatingSortingOrder());
        }
        return movieService.findAll(movieRequest.getPriceSortingOrder());
    }

    @GetMapping("/random")
    protected List<MovieDto> findRandomThree() {
        return movieService.findRandomThree();
    }

    @GetMapping("/genres/{genreId}")
    protected List<MovieDto> findByGenreId(@PathVariable int genreId, MovieRequest movieRequest) {

        if (movieRequest.getRatingSortingOrder() == null && movieRequest.getPriceSortingOrder() == null) {
            return movieService.findByGenreId(genreId);
        }
        if (movieRequest.getRatingSortingOrder() != null && movieRequest.getPriceSortingOrder() != null) {
            return movieService.findByGenreId(
                    genreId, movieRequest.getPriceSortingOrder(), movieRequest.getRatingSortingOrder());
        }
        if (movieRequest.getRatingSortingOrder() != null) {
            return movieService.findByGenreId(genreId, movieRequest.getRatingSortingOrder());
        }
        return movieService.findByGenreId(genreId, movieRequest.getPriceSortingOrder());
    }

    @Data
    public static class MovieRequest {
        private RatingSortingOrder ratingSortingOrder;
        private PriceSortingOrder priceSortingOrder;
    }
}
