package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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

        Genre thirdGenre = Genre.builder()
                .id(3)
                .name("testGenre3")
                .build();

        Mockito.when(genreCache.findAll()).thenReturn(List.of(firstGenre, secondGenre, thirdGenre));
    }

    @Test
    public void testFindAll() {
        List<Genre> genresList = (List<Genre>) genreService.findAll();

        assertEquals(3, genresList.size());

        Genre actual = genresList.get(0);
        assertEquals(1, actual.getId());
        assertEquals("testGenre1", actual.getName());
    }
}
