package com.ohorodnik.movieland.repository.custom;

import com.ohorodnik.movieland.entity.custom.MovieCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepoCustom extends JpaRepository<MovieCustom, Integer> {

    @Query(value = "SELECT country_id FROM movie_country mc WHERE mc.movie_id = %:movieId%", nativeQuery = true)
    List<Integer> findReviewId(Integer movieId);

    @Query(value = "SELECT genre_id FROM movie_genre mg WHERE mg.movie_id = %:movieId%", nativeQuery = true)
    List<Integer> findGenreId(Integer movieId);
}
