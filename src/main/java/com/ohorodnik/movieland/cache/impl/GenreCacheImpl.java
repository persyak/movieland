package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Cache
@RequiredArgsConstructor
public class GenreCacheImpl implements GenreCache {

    private List<Genre> genres;

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreMapper.toGenreDtoList(genres);
    }

    @PostConstruct
    private void initCache() {
        genres = genreRepository.findAll();
    }

    @Scheduled(initialDelayString = "${caching.spring.genreListTTL}", fixedRateString = "${caching.spring.genreListTTL}")
    private void updateCache() {
        genres = genreRepository.findAll();
    }
}
