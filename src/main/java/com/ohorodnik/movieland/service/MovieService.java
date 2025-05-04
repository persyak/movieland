package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.MovieDetailsDto;

import java.util.concurrent.ExecutionException;

public interface MovieService {

    MovieDetailsDto findByIdAndEnrich(Integer movieId) throws ExecutionException, InterruptedException;
}
