package com.ohorodnik.movieland.cache.impl;

import com.ohorodnik.movieland.annotation.Cache;
import com.ohorodnik.movieland.cache.MovieCache;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Cache
@RequiredArgsConstructor
public class MovieCacheImpl implements MovieCache {

    @Value("${caching.movie.size:10}")
    private int capacity;

    private final MovieService movieService;

    private final Map<Integer, SoftReference<MovieNode>> cacheMap = new ConcurrentHashMap<>();
    private final List<Integer> lruList = Collections.synchronizedList(new LinkedList<>());
    private final ReferenceQueue<MovieNode> queue = new ReferenceQueue<>();

    @Override
    public MovieDetailsDto get(Integer movieId) {

        removeEmptyRefs();

        SoftReference<MovieNode> nodeSoftReference = cacheMap.compute(movieId, (k, v) -> {
            if (v == null || v.get() == null) {
                try {
                    MovieDetailsDto movieDetailsDto = movieService.findByIdAndEnrich(movieId);
                    return new SoftReference<>(new MovieNode(movieId, movieDetailsDto), queue);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // Update the reference to the movie node
            lruList.remove(movieId);
            lruList.addFirst(movieId);
            return v;
        });
        return Objects.requireNonNull(nodeSoftReference.get()).movieDetailsDto;
    }

    @Override
    public void put(Integer movieId, MovieDetailsDto editMovieDto) {

        removeEmptyRefs();

        MovieNode movieNode = new MovieNode(movieId, editMovieDto);

        cacheMap.compute(movieId, (k, v) -> {
            if (v == null || v.get() == null) {
                if (cacheMap.size() >= capacity) {

                    // Remove the least recently used item
                    Integer leastUsedKey = lruList.removeLast();
                    cacheMap.remove(leastUsedKey);
                }
                return new SoftReference<>(movieNode, queue);
            }
            lruList.remove(movieId);
            return new SoftReference<>(movieNode, queue);
        });
        // Update the LRU list
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
