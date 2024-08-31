package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultGenreServiceTest {

    @Autowired
    private GenreService genreService;
    @MockBean
    private GenreCache genreCache;

    @BeforeEach
    public void setup() {
        Genre firstGenre = Genre.builder()
                .id(1)
                .name("testGenre1")
                .build();

        Genre secondGenre = Genre.builder()
                .id(2)
                .name("testGenre2")
                .build();

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

        List<Movie> expected = List.of(firstMovie, secondMovie);

        Genre thirdGenre = Genre.builder()
                .id(3)
                .name("testGenre3")
                .movies(expected)
                .build();

        Mockito.when(genreCache.findAll()).thenReturn(List.of(firstGenre, secondGenre, thirdGenre));
        Mockito.when(genreCache.findById(3)).thenReturn(Optional.of(thirdGenre));
    }

    @Test
    public void testFindAll() {
        List<Genre> genresList = (List<Genre>) genreService.findAll();

        assertEquals(3, genresList.size());

        Genre actual = genresList.get(0);
        assertEquals(1, actual.getId());
        assertEquals("testGenre1", actual.getName());
    }

    @Test
    public void testFindById() {
        Optional<Genre> genreOptional = genreService.findById(3);
        assertTrue(genreOptional.isPresent());
        Genre genre = genreOptional.get();
        assertEquals(3, genre.getId());
        assertEquals("testGenre3", genre.getName());
        assertEquals(2, genre.getMovies().size());
    }
}
