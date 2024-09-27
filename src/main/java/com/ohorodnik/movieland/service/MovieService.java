package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();

    List<Movie> findAll(PriceSortingOrder priceSortingOrder);

    List<Movie> findAll(RatingSortingOrder ratingSortingOrder);

    List<Movie> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder);

    List<Movie> findRandomThree();

    List<Movie> findByGenreId(int genreId);

    List<Movie> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder);

    List<Movie> findByGenreId(int genreId, RatingSortingOrder ratingSortingOrder);

    List<Movie> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder);
}
