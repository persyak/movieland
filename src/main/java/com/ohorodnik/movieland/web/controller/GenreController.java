package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    protected List<GenreDto> findAll() {
        log.info("Query get all genres");
        return genreService.findAll();
    }
}
