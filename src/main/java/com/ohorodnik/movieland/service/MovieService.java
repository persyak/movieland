package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll();

    List<MovieDto> findAll(RatingSortingOrder ratingSortingOrder);

    List<MovieDto> findAll(PriceSortingOrder priceSortingOrder);

    List<MovieDto> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder);

    List<MovieDto> findRandomThree();

    List<MovieDto> findByGenreId(Integer genreId);

    List<MovieDto> findByGenreId(Integer genreId, RatingSortingOrder ratingSortingOrder);

    List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder);

    List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder);

    MovieDetailsDto findById(Integer movieId);

    MovieDetailsDto findById(Integer movieId, Currency currency);
}
