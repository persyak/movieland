package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Cache
@RequiredArgsConstructor
public class GenreCacheImpl implements GenreCache {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    //TODO: I think, CopyOnWriteArrayList is not needed anymore as we use reentrantlocks.
    @Immutable
    private final List<GenreDto> genres = new CopyOnWriteArrayList<>();

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    /**
     * @PostConstruct + @Scheduled means, that cash update will be called 1st time before web server starts and
     * once application will be fully started (so twice), but it will save us if somebody or something will call
     * read cache immediately before case is filled.
     */
    @PostConstruct
    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}")
    private void updateCache() {
        writeLock.lock();
        try {
            genres.clear();
            genres.addAll(genreMapper.toGenreDtoList(genreRepository.findAll()));
        } finally {
            writeLock.unlock();
        }
    }

    public List<GenreDto> findAll() {
        readLock.lock();
        try {
            return new ArrayList<>(genres);
        } finally {
            readLock.unlock();
        }
    }
}
