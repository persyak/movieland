package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @GetMapping
    protected List<GenreDto> findAll() {
        return genreMapper.toGenreDtoList(genreService.findAll());
    }
}
