package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.service.impl.DefaultMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DefaultMovieService defaultMovieService;

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

        Mockito.when(defaultMovieService.findAll(Optional.empty(), Optional.empty()))
                .thenReturn(List.of(firstMovie, secondMovie, thirdMovie, fourthdMovie));
        Mockito.when(defaultMovieService.findAll(Optional.of("desc"), Optional.empty()))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        Mockito.when(defaultMovieService.findAll(Optional.empty(), Optional.of("asc")))
                .thenReturn(List.of(firstMovie, secondMovie, fourthdMovie, thirdMovie));
        Mockito.when(defaultMovieService.findAll(Optional.empty(), Optional.of("desc")))
                .thenReturn(List.of(thirdMovie, fourthdMovie, secondMovie, firstMovie));
        Mockito.when(defaultMovieService.findRandomThree())
                .thenReturn(List.of(fourthdMovie, secondMovie, firstMovie));
        Mockito.when(defaultMovieService.findByGenreId(1)).thenReturn(List.of(secondMovie, firstMovie));
    }

    @Test
    public void testFindAll() throws Exception {

        mockMvc.perform(get("/api/v1/movie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1994, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindAllSortedByRatingDesc() throws Exception {

        mockMvc.perform(get("/api/v1/movie?rating=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1999, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));
    }

    @Test
    public void testFindAllSortedByPriceAsc() throws Exception {

        mockMvc.perform(get("/api/v1/movie?price=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1994, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindAllSortedByPriceDesc() throws Exception {

        mockMvc.perform(get("/api/v1/movie?price=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1994, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {

        mockMvc.perform(get("/api/v1/movie/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value("4"))
                .andExpect(jsonPath("$[0].nameUa").value("Список Шиндлера"))
                .andExpect(jsonPath("$[0].nameNative").value("Schindler's List"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1993, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.7"))
                .andExpect(jsonPath("$[0].price").value("150.5"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath4"));
    }

    @Test
    public void testFindByGenreId() throws Exception {

        mockMvc.perform(get("/api/v1/movie/genre/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1999, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));
    }
}
