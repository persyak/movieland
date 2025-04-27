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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class MovieEnrichmentServiceImpl implements MovieEnrichmentService {

    private final MovieRepoCustom movieRepoCustom;
    private final CountryService countryService;
    private final GenreService genreService;
    private final ReviewService reviewService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void enrich(MovieDetailsDto movieDetailsDto, EnrichmentType... enrichmentType) throws ExecutionException, InterruptedException {
        for (EnrichmentType type : enrichmentType) {
            switch (type) {
                case COUNTRIES:
                    Callable<List<CountryDto>> countryTask = () -> countryService
                            .find(movieRepoCustom.findCountryId(movieDetailsDto.getId()));
                    Future<List<CountryDto>> countryFuture = executorService.submit(countryTask);
                    try {
                        movieDetailsDto.setCountries(countryFuture.get(5, TimeUnit.SECONDS));
                    } catch (TimeoutException e) {
                        countryFuture.cancel(true);
                    }
                    break;
                case GENRES:
                    Callable<List<GenreDto>> genreTask = () -> genreService
                            .findByGenreIdList(movieRepoCustom.findGenreId(movieDetailsDto.getId()));
                    Future<List<GenreDto>> genreFuture = executorService.submit(genreTask);
                    try {
                        movieDetailsDto.setGenres(genreFuture.get(5, TimeUnit.SECONDS));
                    } catch (TimeoutException e) {
                        genreFuture.cancel(true);
                    }
                    break;
                case REVIEWS:
                    Callable<List<ReviewDto>> reviewTask = () -> reviewService.findByMovieIdCustom(movieDetailsDto.getId());
                    Future<List<ReviewDto>> reviewFuture = executorService.submit(reviewTask);
                    try {
                        movieDetailsDto.setReviews(reviewFuture.get(5, TimeUnit.SECONDS));
                    } catch (TimeoutException e) {
                        reviewFuture.cancel(true);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown enrichment type: " + type);
            }
        }

    }
}
