package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.service.impl.DefaultGenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DefaultGenreService defaultGenreService;

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

        Mockito.when(defaultGenreService.getAllGenres()).thenReturn(List.of(firstGenre, secondGenre, thirdGenre));
    }

    @Test
    public void testGetAllGenres() throws Exception {

        mockMvc.perform(get("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("testGenre1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("testGenre2"));
    }
}
