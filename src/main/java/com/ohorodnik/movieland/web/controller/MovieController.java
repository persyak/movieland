package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    protected List<MovieDto> findAll(MovieSortingRequest movieSortingRequest) {

        if (movieSortingRequest.getRatingSortingOrder() == null && movieSortingRequest.getPriceSortingOrder() == null) {
            return movieService.findAll();
        }
        if (movieSortingRequest.getRatingSortingOrder() != null && movieSortingRequest.getPriceSortingOrder() != null) {
            return movieService.findAllCustomPriceAndRatingSorting(movieSortingRequest.getPriceSortingOrder());
        }
        if (movieSortingRequest.getRatingSortingOrder() != null) {
            return movieService.findAll(movieSortingRequest.getRatingSortingOrder());
        }
        return movieService.findAll(movieSortingRequest.getPriceSortingOrder());
    }

    @GetMapping("/random")
    protected List<MovieDto> findRandomThree() {
        return movieService.findRandomThree();
    }

    @GetMapping("/genres/{genreId}")
    protected List<MovieDto> findByGenreId(@PathVariable Integer genreId, MovieSortingRequest movieSortingRequest) {

        if (movieSortingRequest.getRatingSortingOrder() == null && movieSortingRequest.getPriceSortingOrder() == null) {
            return movieService.findByGenreId(genreId);
        }
        if (movieSortingRequest.getRatingSortingOrder() != null && movieSortingRequest.getPriceSortingOrder() != null) {
            return movieService.findByGenreId(
                    genreId, movieSortingRequest.getPriceSortingOrder(), movieSortingRequest.getRatingSortingOrder());
        }
        if (movieSortingRequest.getRatingSortingOrder() != null) {
            return movieService.findByGenreId(genreId, movieSortingRequest.getRatingSortingOrder());
        }
        return movieService.findByGenreId(genreId, movieSortingRequest.getPriceSortingOrder());
    }

    @Getter
    @Setter
    private static class MovieSortingRequest {
        private RatingSortingOrder ratingSortingOrder;
        private PriceSortingOrder priceSortingOrder;
    }
}
