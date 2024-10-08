package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m ORDER BY RANDOM() LIMIT 3")
    List<Movie> findRandomThree();

    List<Movie> findByGenres_Id(Integer genreId);

    List<Movie> findByGenres_Id(Integer genreId, Sort sort);
}
