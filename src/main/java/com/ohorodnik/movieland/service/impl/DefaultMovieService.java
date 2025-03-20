package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.entity.custom.MovieCustom;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.repository.custom.MovieRepoCustom;
import com.ohorodnik.movieland.service.CountryService;
import com.ohorodnik.movieland.service.GenreService;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.service.RatesService;
import com.ohorodnik.movieland.service.ReviewService;
import com.ohorodnik.movieland.utils.enums.Currency;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final RatesService ratesService;
    private final CountryService countryService;
    private final GenreService genreService;
    private final ReviewService reviewService;
    private final MovieRepository movieRepository;
    private final MovieRepoCustom movieRepoCustom;
    private final MovieRepositoryCustom movieRepositoryCustom;
    private final MovieMapper movieMapper;
    private final MovieCache movieCache;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

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
    public MovieDetailsDto findById(Integer movieId) throws ExecutionException, InterruptedException {

        MovieDetailsDto cachedMovieDetailsDto = movieCache.get(movieId);
        if (cachedMovieDetailsDto != null) {
            return cachedMovieDetailsDto;
        }

        MovieCustom movieCustom = movieRepoCustom.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found"));

        MovieDetailsDto movieDetailsDto = movieMapper.toMovieDetailsDto(movieCustom);

//        List<Integer> countryIds = movieRepoCustom.findReviewId(movieId);
//        List<CountryDto> countryDtoList = countryService.find(countryIds);

//        List<Integer> genreIds = movieRepoCustom.findGenreId(movieId);
//        List<GenreDto> genreDtoList = genreService.findByGenreIdList(genreIds);

//        List<ReviewDto> reviewDtoList = reviewService.findByMovieIdCustom(movieId);

        Callable<List<CountryDto>> countryTask = () -> countryService.find(movieRepoCustom.findReviewId(movieId));
        Callable<List<GenreDto>> genreTask = () -> genreService.findByGenreIdList(movieRepoCustom.findGenreId(movieId));
        Callable<List<ReviewDto>> reviewTask = () -> reviewService.findByMovieIdCustom(movieId);

        Future<List<CountryDto>> countryFuture = executorService.submit(countryTask);
        Future<List<GenreDto>> genreFuture = executorService.submit(genreTask);
        Future<List<ReviewDto>> reviewFuture = executorService.submit(reviewTask);

        try {
            movieDetailsDto.setCountries(countryFuture.get(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            countryFuture.cancel(true);
        }

        try {
            movieDetailsDto.setGenres(genreFuture.get(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            genreFuture.cancel(true);
        }

        try {
            movieDetailsDto.setReviews(reviewFuture.get(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            reviewFuture.cancel(true);
        }

        movieCache.put(movieId, movieDetailsDto);

        return movieDetailsDto;

//        return movieMapper.toMovieDetailsDto(movieRepository.findById(movieId)
//                .orElseThrow(() -> new MovieNotFoundException("No such movie found")));
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDetailsDto findById(Integer movieId, Currency currency) throws ExecutionException, InterruptedException {
        MovieDetailsDto movieDetailsDto = findById(movieId);

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
    public MovieDetailsDto edit(Integer movieId, EditMovieDto editMovieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("No such movie found"));
        Movie updatedMovie = movieMapper.update(movie, editMovieDto);

        MovieDetailsDto movieDetailsDtoUpdated = movieMapper.toMovieDetailsDto(updatedMovie);
        movieCache.put(movieId, movieDetailsDtoUpdated);

        return movieDetailsDtoUpdated;
    }

    private Double divide(Double a, Double b) {
        //TODO: round result to 2 signs after comma.
        if (b == 0.0) {
            throw new ArithmeticException("divide by zero is not allowed");
        } else {
            return a / b;
        }
    }

    @PreDestroy
    private void shutdown() {
        executorService.shutdown();
    }
}
