package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.entity.SortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies(Optional<String> rating, Optional<String> price) {
        List<Movie> allMovies = movieRepository.getAllMovies();

        BiPredicate<Optional<String>, String> sortingCondition =
                (parameterOptional, sortingOrder) ->
                        parameterOptional.isPresent() && parameterOptional.get().equals(sortingOrder);

        if (sortingCondition.test(rating, String.valueOf(SortingOrder.desc))) {
            allMovies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        }
        if (sortingCondition.test(price, String.valueOf(SortingOrder.asc))) {
            allMovies.sort(Comparator.comparingDouble(Movie::getPrice));
        }
        if (sortingCondition.test(price, String.valueOf(SortingOrder.desc))) {
            allMovies.sort(Comparator.comparingDouble(Movie::getPrice).reversed());
        }
        return allMovies;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> allMovies = movieRepository.getAllMovies();
        Collections.shuffle(allMovies);
        return allMovies.subList(0, 3);
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        return movieRepository.getMoviesByMovieIds(movieRepository.getMovieIdsByGenreId(genreId));
    }
}
