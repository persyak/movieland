package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.entity.Genre;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultMovieServiceTest {

    @Autowired
    private MovieService movieService;
    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private GenreService genreService;

    @BeforeEach
    public void setup() {
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

        Movie secondMovie = Movie.builder()
                .id(2)
                .nameUa("Зелена миля")
                .nameNative("The Green Mile")
                .yearOfRelease(LocalDate.of(1999, 1, 1))
                .description("testDescription2")
                .rating(9.0)
                .price(134.67)
                .picturePath("picturePath2")
                .votes(100)
                .build();

        Movie thirdMovie = Movie.builder()
                .id(3)
                .nameUa("Форест Гамп")
                .nameNative("Forrest Gump")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription3")
                .rating(8.6)
                .price(200.60)
                .picturePath("picturePath3")
                .votes(100)
                .build();

        Movie fourthdMovie = Movie.builder()
                .id(4)
                .nameUa("Список Шиндлера")
                .nameNative("Schindler's List")
                .yearOfRelease(LocalDate.of(1993, 1, 1))
                .description("testDescription4")
                .rating(8.7)
                .price(150.50)
                .picturePath("picturePath4")
                .votes(100)
                .build();

        List<Movie> expected = new ArrayList<>();
        expected.add(firstMovie);
        expected.add(secondMovie);
        expected.add(thirdMovie);
        expected.add(fourthdMovie);

        Genre expectedGenre = Genre.builder()
                .id(1)
                .name("expectedGenre")
                .movies(expected)
                .build();
        Optional<Genre> genreOptional = Optional.of(expectedGenre);

        Mockito.when(movieRepository.findAll()).thenReturn(expected);
        Mockito.when(genreService.findById(1)).thenReturn(genreOptional);
    }

    @Test
    @DisplayName("Get list of four items on findAllMovies call")
    public void whenFindAll_thenReturnListOfFourMovies() {
        List<Movie> found = movieService.findAll(Optional.empty(), Optional.empty());
        Movie actual = found.getFirst();

        assertEquals(4, found.size());
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

    @Test
    @DisplayName("Return list of movies sorted by rating in desc when requested")
    public void whenFindAllWithRatingDesc_thenReturnSortedListByRatingDesc() {
        List<Movie> found = movieService.findAll(Optional.of("desc"), Optional.empty());
        Movie actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(LocalDate.of(1999, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription2", actual.getDescription());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());
        assertEquals(100, actual.getVotes());
    }

    @Test
    @DisplayName("Return list of movies sorted by price in asc when requested")
    public void whenFindAllWithPriceAsc_thenReturnSortedListByPriceAsc() {
        List<Movie> found = movieService.findAll(Optional.empty(), Optional.of("asc"));
        Movie actual = found.getFirst();

        assertEquals(4, found.size());
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

    @Test
    @DisplayName("Return list of movies sorted by price in desc when requested")
    public void whenFindAllWithPriceDesc_thenReturnSortedListByPriceDesc() {
        List<Movie> found = movieService.findAll(Optional.empty(), Optional.of("desc"));
        Movie actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(3, actual.getId());
        assertEquals("Форест Гамп", actual.getNameUa());
        assertEquals("Forrest Gump", actual.getNameNative());
        assertEquals(LocalDate.of(1994, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription3", actual.getDescription());
        assertEquals(8.6, actual.getRating());
        assertEquals(200.60, actual.getPrice());
        assertEquals("picturePath3", actual.getPicturePath());
        assertEquals(100, actual.getVotes());
    }

    @Test
    @DisplayName("Get list of three random movies")
    public void whenFindThreeRandomMoviesRequested_thenReturnListOfThreeMovies() {
        assertEquals(3, movieService.findRandomThree().size());
    }

    @Test
    @DisplayName("Get list of four movies by genre id 1")
    public void whenFindMoviesByGenreId_thenReturnListOfFourMovies() {
        List<Movie> actuals = movieService.findByGenreId(1);
        assertEquals(4, actuals.size());

        Movie actual = actuals.get(1);

        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(LocalDate.of(1999, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription2", actual.getDescription());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());
        assertEquals(100, actual.getVotes());
    }

    @Test
    @DisplayName("Get empty list when movies by genre do not exist")
    public void whenNoMoviesAvailableByGenre_thenGetEmptyList() {
        assertEquals(0, movieService.findByGenreId(2).size());
    }
}
