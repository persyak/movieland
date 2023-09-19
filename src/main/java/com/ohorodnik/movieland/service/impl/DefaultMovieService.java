package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Iterable<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    @Override
    public Iterable<Movie> getThreeRandomMovies() {
        List<Movie> allMovies = StreamSupport
                .stream(movieRepository.getAllMovies().spliterator(), false)
                .collect(Collectors.toList());
        Collections.shuffle(allMovies);
        return allMovies.subList(0, 3);
    }

    @Override
    public Iterable<Movie> getMoviesByGenre(int genreId) {
        return movieRepository.getMoviesByMovieIds(movieRepository.getMovieIdsByGenreId(genreId));
    }
}
