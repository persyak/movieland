package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
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

    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    //TODO: I think, CopyOnWriteArrayList is not needed anymore as we use reentrantlocks.
    @Immutable
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
        writeLock.lock();
        try {
            genres.clear();
            genres.addAll(genreRepository.findAll());
        } finally {
            writeLock.unlock();
        }
    }

    public List<Genre> findAll() {
        readLock.lock();
        try {
            return new ArrayList<>(genres);
        } finally {
            readLock.unlock();
        }
    }
}
