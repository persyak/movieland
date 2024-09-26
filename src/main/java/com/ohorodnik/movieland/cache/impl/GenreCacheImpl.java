package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Cache
@RequiredArgsConstructor
public class GenreCacheImpl implements GenreCache {

    private final List<Genre> genres = new CopyOnWriteArrayList<>();

    private final GenreRepository genreRepository;

    /**
     * @PostConstruct + @Scheduled means, that cash update will be called 1st time before web server starts and
     * once application will be fully started (so twice), but it will save us if somebody or something will call
     * read cache immediately before case is filled.
     */
    @PostConstruct
    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    private void updateCache() {
        genres.clear();
        genres.addAll(genreRepository.findAll());
    }

    public List<Genre> findAll() {
        List<Genre> returnedCachedValues = new ArrayList<>();

        //TODO: think what to do with clone, should be removed.
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
