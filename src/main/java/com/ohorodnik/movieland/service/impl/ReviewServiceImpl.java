package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.entity.Review;
import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.exception.ReviewExistsException;
import com.ohorodnik.movieland.mapper.ReviewMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.ReviewRepository;
import com.ohorodnik.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final UserDetailsService userDetailsService;
    private final ReviewMapper reviewMapper;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDto addReview(AddReviewDto addReviewDto, String email) {
        User user = (User) userDetailsService.loadUserByUsername(email);

        Optional<Review> found = reviewRepository.findByMovie_IdAndUser_Id(addReviewDto.getMovieId(), user.getId());

        if (found.isEmpty()) {
            Movie movie = movieRepository.findById(addReviewDto.getMovieId()).orElseThrow(
                    () -> new MovieNotFoundException("No such movie found"));
            Review reviewToSave = reviewMapper.toReview(addReviewDto);
            reviewToSave.setUser(user);
            reviewToSave.setMovie(movie);
            Review review = reviewRepository.save(reviewToSave);
            return reviewMapper.toReviewDto(review);
        }
        throw new ReviewExistsException(user.getNickname(), addReviewDto.getMovieId());
    }
}
