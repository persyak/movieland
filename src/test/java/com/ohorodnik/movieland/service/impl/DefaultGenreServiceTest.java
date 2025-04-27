package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import com.ohorodnik.movieland.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DefaultGenreServiceTest {

    @Autowired
    private GenreService genreService;
    @MockitoBean
    private GenreRepository genreRepository;

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

        Mockito.when(genreRepository.findAll()).thenReturn(List.of(firstGenre, secondGenre));
    }

    @Test
    public void testFindAll() {
        List<GenreDto> genreDtosList = genreService.findAll();

        assertEquals(2, genreDtosList.size());

        GenreDto actual = genreDtosList.getFirst();
        assertEquals(1, actual.getId());
        assertEquals("testGenre1", actual.getName());
    }
}
