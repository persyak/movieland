package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import org.springframework.beans.factory.annotation.Value;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Cache
public class MovieCacheImpl implements MovieCache {

    @Value("${caching.movie.size:10}")
    private int capacity;

    private final Map<Integer, SoftReference<MovieNode>> cacheMap = new ConcurrentHashMap<>();
    private final LinkedList<Integer> lruList = new LinkedList<>();
    private final ReferenceQueue<MovieNode> queue = new ReferenceQueue<>();

    @Override
    public MovieDetailsDto get(Integer movieId) {

        removeEmptyRefs();

        if (!cacheMap.containsKey(movieId)) {
            return null;
        }

        lruList.remove(movieId);
        lruList.addFirst(movieId);

        return Objects.requireNonNull(cacheMap.get(movieId).get()).movieDetailsDto;
    }

    @Override
    public void put(Integer movieId, MovieDetailsDto editMovieDto) {

        removeEmptyRefs();

        MovieNode movieNode = new MovieNode(movieId, editMovieDto);

        if (cacheMap.containsKey(movieId)) {
            cacheMap.put(movieId, new SoftReference<>(movieNode, queue));

            lruList.remove(movieId);
        } else {
            if (cacheMap.size() >= capacity) {

                // Remove the least recently used item
                Integer leastUsedKey = lruList.removeLast();
                cacheMap.remove(leastUsedKey);
            }
            cacheMap.put(movieId, new SoftReference<>(movieNode, queue));
        }
        lruList.addFirst(movieId);
    }

    private void removeEmptyRefs() {
        while (true) {
            Reference r = queue.poll();
            if (r == null) {
                break;
            }
            MovieNode movieNode = (MovieNode) r.get();
            cacheMap.remove(movieNode.key);
            lruList.remove(movieNode.key);
        }
    }

    static class MovieNode {
        Integer key;
        MovieDetailsDto movieDetailsDto;

        MovieNode(Integer key, MovieDetailsDto movieDetailsDto) {
            this.key = key;
            this.movieDetailsDto = movieDetailsDto;
        }
    }
}
