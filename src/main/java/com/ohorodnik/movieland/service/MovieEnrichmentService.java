package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.MovieDetailsDto;

import java.util.concurrent.ExecutionException;

public interface MovieEnrichmentService {

    void enrich(MovieDetailsDto movieDetailsDto, EnrichmentType... enrichmentType) throws ExecutionException, InterruptedException;

    enum EnrichmentType {
        COUNTRIES,
        GENRES,
        REVIEWS
    }
}
