package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAuthority('U')")
    protected ReviewDto addReview(@Valid @RequestBody AddReviewDto addReviewDto, @NonNull HttpServletRequest request) {
        return reviewService.addReview(addReviewDto, request.getUserPrincipal().getName());
    }
}
