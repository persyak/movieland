package com.ohorodnik.movieland.repository.custom;

import com.ohorodnik.movieland.entity.custom.ReviewCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepoCustom extends JpaRepository<ReviewCustom, Integer> {

    @Query(value = "SELECT * FROM review r WHERE r.movie_id = %:movieId%", nativeQuery = true)
    List<ReviewCustom> findByMovieId(Integer movieId);

    @Query(value = "SELECT user_id FROM review r WHERE r.id = %:reviewId%", nativeQuery = true)
    Integer findUserIdByReviewId(Integer reviewId);
}
