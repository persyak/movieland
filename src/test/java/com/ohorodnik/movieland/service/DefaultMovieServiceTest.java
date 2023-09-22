package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultMovieServiceTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @BeforeEach
    void setup(){
        Movie firstMovie = Movie.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(8.9)
                .price(123.45)
                .picturePath("picturePath1")
                .votes(100)
                .build();

        Mockito.when(movieRepository.getAllMovies()).thenReturn(List.of(firstMovie));
    }

    @Test
    @DisplayName("Get List of One Item on findAllMovies Call")
    public void whenGetAllMovies_thenReturnListOfOneMovie(){
        List<Movie> found = movieService.getAllMovies(java.util.Optional.empty(), java.util.Optional.empty());
        Movie actual = found.get(0);

        assertEquals(1, found.size());
        assertEquals(1, actual.getId());
        assertEquals("Втеча з Шоушенка", actual.getNameUa());
        assertEquals("The Shawshank Redemption", actual.getNameNative());
        assertEquals(LocalDate.of(1994, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription1", actual.getDescription());
        assertEquals(8.9, actual.getRating());
        assertEquals(123.45, actual.getPrice());
        assertEquals("picturePath1", actual.getPicturePath());
        assertEquals(100, actual.getVotes());
    }
}
