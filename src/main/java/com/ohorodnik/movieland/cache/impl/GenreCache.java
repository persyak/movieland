package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.CacheAnnotation;
import com.ohorodnik.movieland.cache.Cache;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@CacheAnnotation
@RequiredArgsConstructor
@EnableScheduling
public class GenreCache implements Cache {

    private final List<Genre> genres = new CopyOnWriteArrayList<>();

    private final GenreService genreService;

    @Override
    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    public void updateCache() {

        genres.clear();
        genres.addAll(genreService.getAllGenres());
    }
    
    public List<Genre> getGenres() {
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
