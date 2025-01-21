package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.AddReviewDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.entity.Country;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.entity.Review;
import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.exception.ReviewExistsException;
import com.ohorodnik.movieland.mapper.ReviewMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.ReviewRepository;
import com.ohorodnik.movieland.utils.enums.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;
    @Autowired
    private ReviewMapper reviewMapper;
    @MockitoBean
    private UserDetailsService userDetailsService;
    @MockitoBean
    private MovieRepository movieRepository;
    @MockitoBean
    private ReviewRepository reviewRepository;

    private static Movie movie;
    private static AddReviewDto addReviewDto;
    private static User user;
    private static Review review;

    @BeforeAll
    public static void beforeAll() {
        movie = Movie.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(8.9)
                .price(140.45)
                .picturePath("picturePath1")
                .countries(List.of(Country.builder().id(1).name("США").build()))
                .genres(List.of(Genre.builder().id(1).name("драма").build(),
                        Genre.builder().id(2).name("кримінал").build()))
                .reviews(List.of(
                        Review.builder().id(1).user(User.builder().id(1).nickname("reviewUser1").build())
                                .description("reviewDescription1").build()))
                .build();

        addReviewDto = AddReviewDto.builder()
                .movieId(1)
                .description("test description ")
                .build();

        user = User.builder()
                .id(1)
                .email("testuser@test.com")
                .password("password".getBytes())
                .nickname("testuser")
                .type(Type.U)
                .build();

        review = Review.builder()
                .id(1)
                .user(user)
                .movie(movie)
                .description("test description")
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.when(userDetailsService.loadUserByUsername("testuser@test.com")).thenReturn(user);
    }


    @Test
    public void addReviewTest() {
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Mockito.when(reviewRepository.findByMovie_IdAndUser_Id(1, 1)).thenReturn(Optional.empty());
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto expectedReviewDto = reviewServiceImpl.addReview(addReviewDto, "testuser@test.com");

        assertEquals(1, expectedReviewDto.getId());
        assertEquals(1, expectedReviewDto.getUser().getId());
        assertEquals("testuser", expectedReviewDto.getUser().getNickname());
        assertEquals("test description", expectedReviewDto.getDescription());
    }

    @Test
    public void whenReviewExists_thenReviewExistsExceptionIsThrown() {
        Mockito.when(reviewRepository.findByMovie_IdAndUser_Id(1, 1)).thenReturn(Optional.of(review));

        Exception exception = assertThrows(ReviewExistsException.class, () -> {
            reviewServiceImpl.addReview(addReviewDto, "testuser@test.com");
        });
        assertEquals("User testuser added review for movie 1 already", exception.getMessage());
    }

    @Test
    public void whenMovieDoesNotExist_thenMovieNotFoundExceptionIsThrown() {
        Mockito.when(reviewRepository.findByMovie_IdAndUser_Id(1, 1)).thenReturn(Optional.empty());
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(MovieNotFoundException.class, () -> {
            reviewServiceImpl.addReview(addReviewDto, "testuser@test.com");
        });

        assertTrue(exception.getMessage().contains("No such movie found"));
    }
}
