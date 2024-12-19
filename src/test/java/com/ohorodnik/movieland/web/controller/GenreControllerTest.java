package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private GenreService genreService;

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

        GenreDto thirdGenreDto = GenreDto.builder()
                .id(3)
                .name("testGenre3")
                .build();

        Mockito.when(genreService.findAll()).thenReturn(List.of(firstGenreDto, secondGenreDto, thirdGenreDto));
    }

    @Test
    public void testFindAll() throws Exception {

        mockMvc.perform(get("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("testGenre1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("testGenre2"));
    }
}
