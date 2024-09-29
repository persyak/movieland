package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll();

    List<MovieDto> findAll(RatingSortingOrder ratingSortingOrder);

    List<MovieDto> findAll(PriceSortingOrder priceSortingOrder);

    List<MovieDto> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder);

    List<MovieDto> findRandomThree();

    List<MovieDto> findByGenreId(int genreId);

    List<MovieDto> findByGenreId(int genreId, RatingSortingOrder ratingSortingOrder);

    List<MovieDto> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder);

    List<MovieDto> findByGenreId(int genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder);
}
