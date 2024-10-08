package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findAndSortByPriceAndRating(String priceSortingOrder);

    List<Movie> findByGenreIdAndSortByPriceAndRating(Integer genreId, String priceSortingOrder);
}
