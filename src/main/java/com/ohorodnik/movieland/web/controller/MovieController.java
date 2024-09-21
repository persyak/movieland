package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping
    protected List<MovieDto> findAll(
            @RequestParam(required = false) RatingSortingOrder ratingSortingOrder,
            @RequestParam(required = false) PriceSortingOrder priceSortingOrder) {

        if (ratingSortingOrder == null && priceSortingOrder == null) {
            return movieMapper.toMovieDtoList(movieService.findAll());
        }
        if (ratingSortingOrder != null && priceSortingOrder != null) {
            return movieMapper.toMovieDtoList(movieService.findAll(priceSortingOrder, ratingSortingOrder));
        }
        if (ratingSortingOrder != null){
            return movieMapper.toMovieDtoList(movieService.findAll(ratingSortingOrder));
        }
        return movieMapper.toMovieDtoList(movieService.findAll(priceSortingOrder));
    }

    @GetMapping("/random")
    protected List<MovieDto> findRandomThree() {
        return movieMapper.toMovieDtoList(movieService.findRandomThree());
    }

    @GetMapping("/genre/{genreId}")
    protected List<MovieDto> findByGenreId(
            @PathVariable int genreId,
            @RequestParam(required = false) RatingSortingOrder ratingSortingOrder,
            @RequestParam(required = false) PriceSortingOrder priceSortingOrder) {

        if (ratingSortingOrder == null && priceSortingOrder == null) {
            return movieMapper.toMovieDtoList(movieService.findByGenreId(genreId));
        }
        if (ratingSortingOrder != null && priceSortingOrder != null) {
            return movieMapper.toMovieDtoList(movieService.findByGenreId(genreId, priceSortingOrder, ratingSortingOrder));
        }
        if (ratingSortingOrder != null){
            return movieMapper.toMovieDtoList(movieService.findByGenreId(genreId, ratingSortingOrder));
        }
        return movieMapper.toMovieDtoList(movieService.findByGenreId(genreId, priceSortingOrder));
    }
}
