package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
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
    private GenreCache genreCache;

    @BeforeEach
    public void setup() {
        GenreDto firstGenreDto = GenreDto.builder()
                .id(1)
                .name("testGenre1")
                .build();

        GenreDto secondGenreDto = GenreDto.builder()
                .id(2)
                .name("testGenre2")
                .build();

        Mockito.when(genreCache.findAll()).thenReturn(List.of(firstGenreDto, secondGenreDto));
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
