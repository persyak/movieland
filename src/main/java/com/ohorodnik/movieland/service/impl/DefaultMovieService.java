package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAll() {
        return movieMapper.toMovieDtoList(movieRepository.findAll());
    }

    @Override
    public List<MovieDto> findAll(RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepository.findAll(
                        Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating")));
    }

    @Override
    public List<MovieDto> findAll(PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepository.findAll(
                        Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price")));
    }

    @Override
    public List<MovieDto> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepositoryCustom.findAndSortByPriceAndRating(priceSortingOrder.toString()));
    }

    @Override
    public List<MovieDto> findRandomThree() {
        return movieMapper.toMovieDtoList(movieRepository.findRandomThree());
    }

    @Override
    public List<MovieDto> findByGenreId(Integer genreId) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(genreId));
    }

    @Override
    public List<MovieDto> findByGenreId(Integer genreId, RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating")));
    }

    @Override
    public List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price")));
    }

    @Override
    public List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepositoryCustom.findByGenreIdAndSortByPriceAndRating(
                genreId, priceSortingOrder.toString()));
    }

    @Override
    //TODO: after we get reviews, it iterates over them and queries DB for user for each review separately.
    //TODO: define if it's possible to querie users in one select.
    public MovieDetailsDto findById(Integer movieId) {
        return movieMapper.toMovieDetailsDto(movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found")));
    }
}
