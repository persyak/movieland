package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    protected List<MovieDto> findAll(MovieSortingRequest movieSortingRequest) {
        log.info("Query get all movies");

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
        log.info("Query get random three movies");
        return movieService.findRandomThree();
    }

    @GetMapping("/genres/{genreId}")
    protected List<MovieDto> findByGenreId(@PathVariable Integer genreId, MovieSortingRequest movieSortingRequest) {
        log.info("Query get movies by genre id {}", genreId);

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

    @GetMapping("/movie/{movieId}")
    protected MovieDetailsDto findById(@PathVariable Integer movieId, Currency currency) {
        log.info("Query movie details by movie id {}", movieId);
        return currency == null ? movieService.findById(movieId) : movieService.findById(movieId, currency);
    }

    @Getter
    @Setter
    public static class MovieSortingRequest {
        private RatingSortingOrder ratingSortingOrder;
        private PriceSortingOrder priceSortingOrder;
    }
}
