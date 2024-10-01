package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Cache
@RequiredArgsConstructor
public class GenreCacheImpl implements GenreCache {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private final List<GenreDto> genreDtos = new ArrayList<>();

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    /**
     * @PostConstruct + @Scheduled means, that cash update will be called 1st time before web server starts and
     * once application will be fully started (so twice), but it will save us if somebody or something will call
     * read cache immediately before case is filled.
     */

    //TODO: try to think if it makes sense to do post construct somehow so it does not stop server start
    @PostConstruct
    @Scheduled(fixedRateString = "${caching.spring.genreListTTL}", initialDelay = 14400000)
    private void updateCache() {
        writeLock.lock();
        try {
            genreDtos.clear();
            genreDtos.addAll(genreMapper.toGenreDtoList(genreRepository.findAll()));
        } finally {
            writeLock.unlock();
        }
    }

    public List<GenreDto> findAll() {
        readLock.lock();
        try {
            return new ArrayList<>(genreDtos);
        } finally {
            readLock.unlock();
        }
    }
}
