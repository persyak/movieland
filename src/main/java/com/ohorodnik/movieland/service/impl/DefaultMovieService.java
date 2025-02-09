package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.service.RatesService;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final RatesService ratesService;
    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAll() {
        return movieMapper.toMovieDtoList(movieRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAll(RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepository.findAll(
                        Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAll(PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepository.findAll(
                        Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAllCustomPriceAndRatingSorting(PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(
                movieRepositoryCustom.findAndSortByPriceAndRating(priceSortingOrder.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findRandomThree() {
        return movieMapper.toMovieDtoList(movieRepository.findRandomThree());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByGenreId(Integer genreId) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(genreId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByGenreId(Integer genreId, RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(ratingSortingOrder.toString()), "rating")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepository.findByGenres_Id(
                genreId, Sort.by(Sort.Direction.fromString(priceSortingOrder.toString()), "price")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByGenreId(Integer genreId, PriceSortingOrder priceSortingOrder, RatingSortingOrder ratingSortingOrder) {
        return movieMapper.toMovieDtoList(movieRepositoryCustom.findByGenreIdAndSortByPriceAndRating(
                genreId, priceSortingOrder.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    //TODO: after we get reviews, it iterates over them and queries DB for user for each review separately.
    //TODO: define if it's possible to querie users in one select.
    public MovieDetailsDto findById(Integer movieId) {
        return movieMapper.toMovieDetailsDto(movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found")));
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDetailsDto findById(Integer movieId, Currency currency) {
        MovieDetailsDto movieDetailsDto = movieMapper.toMovieDetailsDto(movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found")));

        Double rate = ratesService.getRate(currency);
        double priceForeighCurrency = divide(movieDetailsDto.getPrice(), rate);

        movieDetailsDto.setPrice(priceForeighCurrency);

        return movieDetailsDto;
    }

    @Override
    @Transactional
    public MovieDetailsDto add(AddMovieDto addMovieDto) {
        Movie movie = movieMapper.toMovie(addMovieDto);
        movie.setRating((double) 0);
        return movieMapper.toMovieDetailsDto(movieRepository.save(movie));
    }

    @Override
    @Transactional
    public MovieDetailsDto edit(Integer id, EditMovieDto editMovieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("No such movie found"));
        Movie updatedMovie = movieMapper.update(movie, editMovieDto);
        return movieMapper.toMovieDetailsDto(updatedMovie);
    }

    private Double divide(Double a, Double b) {
        //TODO: round result to 2 signs after comma.
        if (b == 0.0) {
            throw new ArithmeticException("divide by zero is not allowed");
        } else {
            return a / b;
        }
    }
}
