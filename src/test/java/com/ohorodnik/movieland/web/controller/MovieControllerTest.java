package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.service.MovieService;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MovieDto firstMovieDto = MovieDto.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(Year.of(1994))
                .rating(8.9)
                .price(123.45)
                .picturePath("picturePath1")
                .build();

        MovieDto secondMovieDto = MovieDto.builder()
                .id(2)
                .nameUa("Зелена миля")
                .nameNative("The Green Mile")
                .yearOfRelease(Year.of(1999))
                .rating(9.0)
                .price(134.67)
                .picturePath("picturePath2")
                .build();

        MovieDto thirdMovieDto = MovieDto.builder()
                .id(3)
                .nameUa("Форест Гамп")
                .nameNative("Forrest Gump")
                .yearOfRelease(Year.of(1994))
                .rating(8.6)
                .price(200.60)
                .picturePath("picturePath3")
                .build();

        MovieDto fourthdMovieDto = MovieDto.builder()
                .id(4)
                .nameUa("Список Шиндлера")
                .nameNative("Schindler's List")
                .yearOfRelease(Year.of(1993))
                .rating(8.7)
                .price(150.50)
                .picturePath("picturePath4")
                .build();

        Mockito.when(movieService.findAll())
                .thenReturn(List.of(firstMovieDto, secondMovieDto, thirdMovieDto, fourthdMovieDto));
        Mockito.when(movieService.findAll(RatingSortingOrder.desc))
                .thenReturn(List.of(secondMovieDto, firstMovieDto, fourthdMovieDto, thirdMovieDto));
        Mockito.when(movieService.findAll(PriceSortingOrder.asc))
                .thenReturn(List.of(firstMovieDto, secondMovieDto, fourthdMovieDto, thirdMovieDto));
        Mockito.when(movieService.findAll(PriceSortingOrder.desc))
                .thenReturn(List.of(thirdMovieDto, fourthdMovieDto, secondMovieDto, firstMovieDto));
        Mockito.when(movieService.findAllCustomPriceAndRatingSorting(PriceSortingOrder.asc))
                .thenReturn(List.of(firstMovieDto, secondMovieDto, fourthdMovieDto, thirdMovieDto));
        Mockito.when(movieService.findAllCustomPriceAndRatingSorting(PriceSortingOrder.desc))
                .thenReturn(List.of(thirdMovieDto, fourthdMovieDto, secondMovieDto, firstMovieDto));
        Mockito.when(movieService.findRandomThree())
                .thenReturn(List.of(fourthdMovieDto, secondMovieDto, firstMovieDto));
        Mockito.when(movieService.findByGenreId(1)).thenReturn(List.of(firstMovieDto, secondMovieDto));
        Mockito.when(movieService.findByGenreId(1, RatingSortingOrder.desc))
                .thenReturn(List.of(secondMovieDto, firstMovieDto));
        Mockito.when(movieService.findByGenreId(2, PriceSortingOrder.asc))
                .thenReturn(List.of(fourthdMovieDto, thirdMovieDto));
        Mockito.when(movieService.findByGenreId(2, PriceSortingOrder.desc))
                .thenReturn(List.of(thirdMovieDto, fourthdMovieDto));
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindAllSortedByRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies?ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1999).toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));
    }

    @Test
    public void testFindAllSortedByPriceAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindAllSortedByPriceDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));
    }

    @Test
    public void testFindAllSortedByPriceAscRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=asc&ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindAllSortedByPriceDescRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=desc&ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {
        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value("4"))
                .andExpect(jsonPath("$[0].nameUa").value("Список Шиндлера"))
                .andExpect(jsonPath("$[0].nameNative").value("Schindler's List"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1993).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.7"))
                .andExpect(jsonPath("$[0].price").value("150.5"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath4"));
    }

    @Test
    public void testFindByGenreId() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genres/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));
    }

    @Test
    public void testFindByGenreIdSortedByRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genres/1?ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1999).toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));
    }

    @Test
    public void testFindByGenreIdSortedByPriceAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genres/2?priceSortingOrder=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("4"))
                .andExpect(jsonPath("$[0].nameUa").value("Список Шиндлера"))
                .andExpect(jsonPath("$[0].nameNative").value("Schindler's List"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1993).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.7"))
                .andExpect(jsonPath("$[0].price").value("150.5"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath4"));
    }

    @Test
    public void testFindByGenreIdSortedByPriceDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genres/2?priceSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));
    }
}
