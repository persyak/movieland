package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
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

    @Override
    public List<Genre> findAll() {
        return genres;
    }

    @Override
    public List<Genre> findByIdList(List<Integer> ids) {
        return genres.stream().filter(genre -> ids.contains(genre.getId())).toList();
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
