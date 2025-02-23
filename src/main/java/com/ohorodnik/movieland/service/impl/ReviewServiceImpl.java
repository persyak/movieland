package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.dto.UserDto;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.entity.Review;
import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.entity.custom.ReviewCustom;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.exception.ReviewExistsException;
import com.ohorodnik.movieland.mapper.ReviewMapper;
import com.ohorodnik.movieland.mapper.UserMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.ReviewRepository;
import com.ohorodnik.movieland.repository.custom.ReviewRepoCustom;
import com.ohorodnik.movieland.service.ReviewService;
import com.ohorodnik.movieland.service.UserDetailsServiceExtention;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final UserDetailsServiceExtention userDetailsServiceExtention;
    private final ReviewMapper reviewMapper;
    private final UserMapper userMapper;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewRepoCustom reviewRepoCustom;

    @Override
    public ReviewDto addReview(AddReviewDto addReviewDto, String email) {
        User user = (User) userDetailsServiceExtention.loadUserByUsername(email);

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

    @Override
    public List<ReviewDto> findByMovieIdCustom(Integer movieId) {
        List<ReviewCustom> reviews = reviewRepoCustom.findByMovieId(movieId);
        List<ReviewDto> reviewDtoList = reviewMapper.toReviewDtoListFromCustom(reviews);
        for(ReviewDto reviewDto: reviewDtoList){
            Integer userId = reviewRepoCustom.findUserIdByReviewId(reviewDto.getId());
            User user = (User) userDetailsServiceExtention.findUserById(userId);
            UserDto userDto = userMapper.toUserDto(user);
            reviewDto.setUser(userDto);
        }
        return reviewDtoList;
    }
}
