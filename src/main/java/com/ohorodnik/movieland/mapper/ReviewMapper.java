package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ReviewMapper {

    ReviewDto toReviewDto(Review review);

    List<ReviewDto> toReviewDtoList(List<Review> reviews);
}