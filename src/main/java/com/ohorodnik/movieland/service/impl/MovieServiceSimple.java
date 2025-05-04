package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.entity.custom.MovieCustom;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.custom.MovieRepoCustom;
import com.ohorodnik.movieland.service.MovieEnrichmentService;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MovieServiceSimple implements MovieService {

    private final MovieRepoCustom movieRepoCustom;
    private final MovieMapper movieMapper;
    private final MovieEnrichmentService movieEnrichmentService;

    @Override
    public MovieDetailsDto findByIdAndEnrich(Integer movieId) throws ExecutionException, InterruptedException {
        MovieCustom movieCustom = movieRepoCustom.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found"));

        MovieDetailsDto movieDetailsDto = movieMapper.toMovieDetailsDto(movieCustom);

        movieEnrichmentService.enrich(movieDetailsDto,
                MovieEnrichmentService.EnrichmentType.COUNTRIES,
                MovieEnrichmentService.EnrichmentType.GENRES,
                MovieEnrichmentService.EnrichmentType.REVIEWS);

        return movieDetailsDto;
    }
}
