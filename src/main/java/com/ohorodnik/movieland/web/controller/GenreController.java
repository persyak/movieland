package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.cache.impl.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreCache genreCache;

    @GetMapping
    protected Iterable<Genre> getAllGenres() {
        return genreCache.getGenres();
    }
}
