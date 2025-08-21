package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.dto.events.MovieEventDto;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.service.MessagingService;
import com.ohorodnik.movieland.service.MovieCachedService;
import com.ohorodnik.movieland.service.RatesService;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ohorodnik.movieland.utils.enums.MovieEventType.NEW;
import static com.ohorodnik.movieland.utils.enums.MovieEventType.UPDATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultMovieCachedService implements MovieCachedService {

    private final RatesService ratesService;
    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;
    private final MovieMapper movieMapper;
    private final MovieCache movieCache;
    private final MessagingService messagingService;

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
        return movieCache.get(movieId);
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
        MovieDetailsDto movieDetailsDto = movieMapper.toMovieDetailsDto(movieRepository.save(movie));
        MovieEventDto movieEventDto = MovieEventDto.builder()
                .movieEventId(movieDetailsDto.getId())
                .movieEventType(NEW)
                .movieDetailsDto(movieDetailsDto)
                .build();
        messagingService.sendMessage(movieEventDto);
        return movieDetailsDto;
    }

    @Override
    @Transactional
    public MovieDetailsDto edit(Integer movieId, EditMovieDto editMovieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("No such movie found"));
        Movie updatedMovie = movieMapper.update(movie, editMovieDto);

        MovieDetailsDto movieDetailsDtoUpdated = movieMapper.toMovieDetailsDto(updatedMovie);
        movieCache.put(movieId, movieDetailsDtoUpdated);

        MovieEventDto movieUpdateEventDto = MovieEventDto.builder()
                .movieEventId(movieDetailsDtoUpdated.getId())
                .movieEventType(UPDATE)
                .movieDetailsDto(movieDetailsDtoUpdated)
                .build();
        messagingService.sendMessage(movieUpdateEventDto);

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
