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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@CacheAnnotation
@RequiredArgsConstructor
@EnableScheduling
public class GenreCacheImpl implements GenreCache {

    Map<Integer, Genre> genres = new ConcurrentHashMap<>();

    private final GenreRepository genreRepository;

    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    private void updateCache() {
        genreRepository.findAll().forEach(genre -> genres.put(genre.getId(), genre));
    }

    public List<Genre> findAll() {
        List<Genre> returnedCachedValues = new ArrayList<>();

        genres.values().forEach(genre -> {
            try {
                returnedCachedValues.add(genre.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        return returnedCachedValues;
    }

    public Optional<Genre> findById(int id) {
        return Optional.ofNullable(genres.get(id));
    }
}
