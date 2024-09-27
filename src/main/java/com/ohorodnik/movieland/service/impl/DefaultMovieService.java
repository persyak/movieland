package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findAll(PriceSortingOrder priceSortingOrder) {
        return movieRepository.findAll(Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price"));
    }

    @Override
    public List<Movie> findAll(RatingSortingOrder ratingSortingOrder) {
        return movieRepository.findAll(Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating"));
    }

    @Override
    public List<Movie> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder) {
        return movieRepositoryCustom.findAndSortByPriceAndRating(priceSortingOrder.toString());
    }

    @Override
    public List<Movie> findRandomThree() {
        return movieRepository.findRandomThree();
    }

    @Override
    public List<Movie> findByGenreId(int genreId) {
        return movieRepository.findByGenres_Id(genreId);
    }

    @Override
    public List<Movie> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder) {
        return movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price"));
    }

    @Override
    public List<Movie> findByGenreId(int genreId, RatingSortingOrder ratingSortingOrder) {
        return movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating"));
    }

    @Override
    public List<Movie> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder) {
        return movieRepositoryCustom.findByGenreIdAndSortByPriceAndRating(
                genreId, priceSortingOrder.toString(), ratingSortingOrder.toString());
    }
}
