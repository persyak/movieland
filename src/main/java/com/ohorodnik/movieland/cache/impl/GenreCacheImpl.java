package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.CacheAnnotation;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@CacheAnnotation
@RequiredArgsConstructor
@EnableScheduling
public class GenreCacheImpl implements GenreCache {

    private final List<Genre> genres = new CopyOnWriteArrayList<>();

    private final GenreRepository genreRepository;

    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    private void updateCache() {

        genres.clear();
        genres.addAll((List<Genre>) genreRepository.findAll());
    }

    public List<Genre> findAll() {
        List<Genre> returnedCachedValues = new ArrayList<>();

        genres.forEach(genre -> {
            try {
                returnedCachedValues.add(genre.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        return returnedCachedValues;
    }
}
