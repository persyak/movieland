package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByMovie_IdAndUser_Id(Integer movieId, Integer userId);
}
