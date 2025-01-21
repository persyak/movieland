package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;

public interface ReviewService {

    ReviewDto addReview(AddReviewDto addReviewDto, String email);
}
