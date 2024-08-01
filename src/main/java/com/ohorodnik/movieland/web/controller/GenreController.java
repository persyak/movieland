package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    protected Iterable<Genre> findAll() {
        return genreService.findAll();
    }
}
