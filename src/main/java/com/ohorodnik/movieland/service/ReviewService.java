package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto addReview(AddReviewDto addReviewDto, String email);

    List<ReviewDto> findByMovieIdCustom(Integer movieId);
}
