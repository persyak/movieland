package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findAndSortByPriceAndRating(String priceSortingOrder, String ratingSortingOrder);

    List<Movie> findByGenreIdAndSortByPriceAndRating(int genreId, String priceSortingOrder, String ratingSortingOrder);
}
