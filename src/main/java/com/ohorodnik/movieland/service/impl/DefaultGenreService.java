package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Cacheable("genres")
    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    //TODO: think if we can apply more interesting cache than this simple one.
    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    @CacheEvict(value = "genres", allEntries = true)
    public void evictGenresCache(){}
}
