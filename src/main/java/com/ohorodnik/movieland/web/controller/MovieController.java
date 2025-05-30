package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.service.MovieCachedService;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieCachedService movieCachedService;

    @GetMapping
    protected List<MovieDto> findAll(MovieSortingRequest movieSortingRequest) {
        log.info("Query get all movies");

        if (movieSortingRequest.getRatingSortingOrder() == null && movieSortingRequest.getPriceSortingOrder() == null) {
            return movieCachedService.findAll();
        }
        if (movieSortingRequest.getRatingSortingOrder() != null && movieSortingRequest.getPriceSortingOrder() != null) {
            return movieCachedService.findAllCustomPriceAndRatingSorting(movieSortingRequest.getPriceSortingOrder());
        }
        if (movieSortingRequest.getRatingSortingOrder() != null) {
            return movieCachedService.findAll(movieSortingRequest.getRatingSortingOrder());
        }
        return movieCachedService.findAll(movieSortingRequest.getPriceSortingOrder());
    }

    @GetMapping("/random")
    protected List<MovieDto> findRandomThree() {
        log.info("Query get random three movies");
        return movieCachedService.findRandomThree();
    }

    @GetMapping("/genres/{genreId}")
    protected List<MovieDto> findByGenreId(@PathVariable Integer genreId, MovieSortingRequest movieSortingRequest) {
        log.info("Query get movies by genre id {}", genreId);

        if (movieSortingRequest.getRatingSortingOrder() == null && movieSortingRequest.getPriceSortingOrder() == null) {
            return movieCachedService.findByGenreId(genreId);
        }
        if (movieSortingRequest.getRatingSortingOrder() != null && movieSortingRequest.getPriceSortingOrder() != null) {
            return movieCachedService.findByGenreId(
                    genreId, movieSortingRequest.getPriceSortingOrder(), movieSortingRequest.getRatingSortingOrder());
        }
        if (movieSortingRequest.getRatingSortingOrder() != null) {
            return movieCachedService.findByGenreId(genreId, movieSortingRequest.getRatingSortingOrder());
        }
        return movieCachedService.findByGenreId(genreId, movieSortingRequest.getPriceSortingOrder());
    }

    @GetMapping("/movie/{id}")
    protected MovieDetailsDto findById(@PathVariable Integer id, Currency currency) throws ExecutionException, InterruptedException {
        log.info("Query movie details by movie id {}", id);
        return currency == null ? movieCachedService.findById(id) : movieCachedService.findById(id, currency);
    }

    @PostMapping("/movie")
    @PreAuthorize("hasAuthority('A')")
    @ResponseStatus(HttpStatus.CREATED)
    protected MovieDetailsDto add(@Valid @RequestBody AddMovieDto addMovieDto) {
        return movieCachedService.add(addMovieDto);
    }

    @PutMapping("/movie/{id}")
    @PreAuthorize("hasAuthority('A')")
    protected MovieDetailsDto edit(@PathVariable Integer id, @Valid @RequestBody EditMovieDto editMovieDto) {
        MovieDetailsDto movieDetailsDto = movieCachedService.edit(id, editMovieDto);
        log.info("Movie {} has been updated", id);
        return movieDetailsDto;
    }

    @Getter
    @Setter
    public static class MovieSortingRequest {
        private RatingSortingOrder ratingSortingOrder;
        private PriceSortingOrder priceSortingOrder;
    }
}
