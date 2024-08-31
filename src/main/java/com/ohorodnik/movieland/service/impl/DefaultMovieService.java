package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.service.GenreService;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.entity.SortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    @Value("${utils.randomMoviesAmount}")
    private int randomMoviesAmount;
    private final MovieRepository movieRepository;
    private final GenreService genreService;

    @Override
    public List<Movie> findAll(Optional<String> rating, Optional<String> price) {
        List<Movie> allMovies = movieRepository.findAll();
        return sort(rating, price, allMovies);
    }

    @Override
    public List<Movie> findRandomThree() {
        List<Movie> allMovies = movieRepository.findAll();
        Collections.shuffle(allMovies);
        return allMovies.subList(0, randomMoviesAmount);
    }

    @Override
    public List<Movie> findByGenreId(int genreId) {
        Optional<Genre> genreOptional = genreService.findById(genreId);
        return genreOptional.isPresent() ? genreOptional.get().getMovies() : List.of();
    }

    private List<Movie> sort(Optional<String> rating, Optional<String> price, List<Movie> allMovies) {
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
}
