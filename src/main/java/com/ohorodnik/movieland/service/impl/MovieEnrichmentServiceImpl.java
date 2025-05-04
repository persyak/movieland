package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.repository.custom.MovieRepoCustom;
import com.ohorodnik.movieland.service.CountryService;
import com.ohorodnik.movieland.service.GenreService;
import com.ohorodnik.movieland.service.MovieEnrichmentService;
import com.ohorodnik.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class MovieEnrichmentServiceImpl implements MovieEnrichmentService {

    private final MovieRepoCustom movieRepoCustom;
    private final CountryService countryService;
    private final GenreService genreService;
    private final ReviewService reviewService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void enrich(MovieDetailsDto movieDetailsDto, EnrichmentType... enrichmentType) {

        List<Runnable> tasks = new ArrayList<>();

        for (EnrichmentType type : enrichmentType) {
            switch (type) {
                case COUNTRIES:
                    tasks.add(() -> {
                        List<CountryDto> countries = countryService
                                .find(movieRepoCustom.findCountryId(movieDetailsDto.getId()));
                        movieDetailsDto.setCountries(countries);
                    });
                    break;
                case GENRES:
                    tasks.add(() -> {
                        List<GenreDto> genres = genreService
                                .findByGenreIdList(movieRepoCustom.findGenreId(movieDetailsDto.getId()));
                        movieDetailsDto.setGenres(genres);
                    });
                    break;
                case REVIEWS:
                    tasks.add(() -> {
                        List<ReviewDto> reviews = reviewService
                                .findByMovieIdCustom(movieDetailsDto.getId());
                        movieDetailsDto.setReviews(reviews);
                    });
                    break;
                default:
                    throw new IllegalArgumentException("Unknown enrichment type: " + type);
            }
        }

        List<Callable<Object>> callables = tasks.stream().map(Executors::callable).toList();

        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
