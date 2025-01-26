package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.AddCountryDto;
import com.ohorodnik.movieland.dto.AddGenreDto;
import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.dto.ReviewDto;
import com.ohorodnik.movieland.dto.UserDto;
import com.ohorodnik.movieland.entity.Country;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.entity.Review;
import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.exception.MovieNotFoundException;
import com.ohorodnik.movieland.mapper.MovieMapper;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DefaultMovieServiceTest {

    @Autowired
    private DefaultMovieService defaultMovieService;
    @MockitoBean
    private MovieMapper movieMapper;
    @MockitoBean
    private MovieRepository movieRepository;
    @MockitoBean
    private MovieRepositoryCustom movieRepositoryCustom;

    @BeforeEach
    public void setup() {
        Movie firstMovie = Movie.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(8.9)
                .price(140.45)
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

        MovieDto movieDtoFirst = MovieDto.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(Year.of(1994))
                .rating(8.9)
                .price(140.45)
                .picturePath("picturePath1")
                .build();

        MovieDto movieDtoSecond = MovieDto.builder()
                .id(2)
                .nameUa("Зелена миля")
                .nameNative("The Green Mile")
                .yearOfRelease(Year.of(1999))
                .rating(9.0)
                .price(134.67)
                .picturePath("picturePath2")
                .build();

        MovieDto movieDtoThird = MovieDto.builder()
                .id(3)
                .nameUa("Форест Гамп")
                .nameNative("Forrest Gump")
                .yearOfRelease(Year.of(1994))
                .rating(8.6)
                .price(200.60)
                .picturePath("picturePath3")
                .build();

        MovieDto movieDtoFourth = MovieDto.builder()
                .id(4)
                .nameUa("Список Шиндлера")
                .nameNative("Schindler's List")
                .yearOfRelease(Year.of(1993))
                .rating(8.7)
                .price(150.50)
                .picturePath("picturePath4")
                .build();

        when(movieRepository.findAll()).thenReturn(List.of(firstMovie, secondMovie, thirdMovie, fourthdMovie));
        when(movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(RatingSortingOrder.desc.toString()), "rating")))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        when(movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.asc.toString()), "price")))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        when(movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.desc.toString()), "price")))
                .thenReturn(List.of(thirdMovie, fourthdMovie, secondMovie, firstMovie));
        when(movieRepository.findRandomThree()).thenReturn(List.of(firstMovie, secondMovie, thirdMovie));
        when(movieRepository.findByGenres_Id(1))
                .thenReturn(List.of(firstMovie, secondMovie, thirdMovie, fourthdMovie));
        when(movieRepository.findByGenres_Id(
                1,
                Sort.by(Sort.Direction.fromString(RatingSortingOrder.desc.toString()),
                        "rating")))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        when(movieRepository.findByGenres_Id(
                1,
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.asc.toString()),
                        "price")))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        when(movieRepository.findByGenres_Id(
                1,
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.desc.toString()),
                        "price")))
                .thenReturn(List.of(thirdMovie, fourthdMovie, secondMovie, firstMovie));

        when(movieRepositoryCustom.findAndSortByPriceAndRating(PriceSortingOrder.asc.toString()))
                .thenReturn(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie));
        when(movieRepositoryCustom.findAndSortByPriceAndRating(PriceSortingOrder.desc.toString()))
                .thenReturn(List.of(thirdMovie, fourthdMovie, secondMovie, firstMovie));

        when(movieMapper.toMovieDtoList(List.of(firstMovie, secondMovie, thirdMovie, fourthdMovie)))
                .thenReturn(List.of(movieDtoFirst, movieDtoSecond, movieDtoThird, movieDtoFourth));
        when(movieMapper.toMovieDtoList(List.of(secondMovie, firstMovie, fourthdMovie, thirdMovie)))
                .thenReturn(List.of(movieDtoSecond, movieDtoFirst, movieDtoFourth, movieDtoThird));
        when(movieMapper.toMovieDtoList(List.of(thirdMovie, fourthdMovie, secondMovie, firstMovie)))
                .thenReturn(List.of(movieDtoThird, movieDtoFourth, movieDtoSecond, movieDtoFirst));
        when(movieMapper.toMovieDtoList(List.of(firstMovie, secondMovie, thirdMovie)))
                .thenReturn(List.of(movieDtoFirst, movieDtoSecond, movieDtoThird));
    }

    @Test
    @DisplayName("Get list of four items on findAllMovies call")
    public void whenFindAll_thenReturnListOfFourMovies() {
        List<MovieDto> found = defaultMovieService.findAll();
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(1, actual.getId());
        assertEquals("Втеча з Шоушенка", actual.getNameUa());
        assertEquals("The Shawshank Redemption", actual.getNameNative());
        assertEquals(Year.of(1994), actual.getYearOfRelease());
        assertEquals(8.9, actual.getRating());
        assertEquals(140.45, actual.getPrice());
        assertEquals("picturePath1", actual.getPicturePath());
    }

    @Test
    @DisplayName("Return list of movies sorted by rating in desc when requested")
    public void whenFindAllWithRatingDesc_thenReturnSortedListByRatingDesc() {
        List<MovieDto> found = defaultMovieService.findAll(RatingSortingOrder.desc);
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(Year.of(1999), actual.getYearOfRelease());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());

        assertEquals(1, found.get(1).getId());
    }

    @Test
    @DisplayName("Return list of movies sorted by price in asc when requested")
    public void whenFindAllWithPriceAsc_thenReturnSortedListByPriceAsc() {
        List<MovieDto> found = defaultMovieService.findAll(PriceSortingOrder.asc);
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(Year.of(1999), actual.getYearOfRelease());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());

        assertEquals(1, found.get(1).getId());
    }

    @Test
    @DisplayName("Return list of movies sorted by price in desc when requested")
    public void whenFindAllWithPriceAsc_thenReturnSortedListByPriceDesc() {
        List<MovieDto> found = defaultMovieService.findAll(PriceSortingOrder.desc);
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(3, actual.getId());
        assertEquals("Форест Гамп", actual.getNameUa());
        assertEquals("Forrest Gump", actual.getNameNative());
        assertEquals(Year.of(1994), actual.getYearOfRelease());
        assertEquals(8.6, actual.getRating());
        assertEquals(200.60, actual.getPrice());
        assertEquals("picturePath3", actual.getPicturePath());

        assertEquals(4, found.get(1).getId());
    }

    @Test
    @DisplayName("Return list of movies sorted by price in asc and rating when requested")
    public void testFindAllCustomPriceAscAndRatingSorting() {
        List<MovieDto> found = defaultMovieService.findAllCustomPriceAndRatingSorting(PriceSortingOrder.asc);
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(Year.of(1999), actual.getYearOfRelease());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());

        assertEquals(1, found.get(1).getId());
    }

    @Test
    @DisplayName("Return list of movies sorted by price in desc and rating when requested")
    public void testFindAllCustomPriceDescAndRatingSorting() {
        List<MovieDto> found = defaultMovieService.findAllCustomPriceAndRatingSorting(PriceSortingOrder.desc);
        MovieDto actual = found.getFirst();

        assertEquals(4, found.size());
        assertEquals(3, actual.getId());
        assertEquals("Форест Гамп", actual.getNameUa());
        assertEquals("Forrest Gump", actual.getNameNative());
        assertEquals(Year.of(1994), actual.getYearOfRelease());
        assertEquals(8.6, actual.getRating());
        assertEquals(200.60, actual.getPrice());
        assertEquals("picturePath3", actual.getPicturePath());

        assertEquals(4, found.get(1).getId());
    }

    @Test
    @DisplayName("Get list of three random movies")
    public void whenFindThreeRandomMoviesRequested_thenReturnListOfThreeMovies() {
        assertEquals(3, defaultMovieService.findRandomThree().size());
    }

    @Test
    @DisplayName("Get list of four movies by genre id 1")
    public void whenFindMoviesByGenreId_thenReturnListOfFourMovies() {
        List<MovieDto> found = defaultMovieService.findByGenreId(1);
        assertEquals(4, found.size());

        MovieDto actual = found.getFirst();

        assertEquals(1, actual.getId());
        assertEquals("Втеча з Шоушенка", actual.getNameUa());
        assertEquals("The Shawshank Redemption", actual.getNameNative());
        assertEquals(Year.of(1994), actual.getYearOfRelease());
        assertEquals(8.9, actual.getRating());
        assertEquals(140.45, actual.getPrice());
        assertEquals("picturePath1", actual.getPicturePath());
    }

    @Test
    @DisplayName("Get list of four movies by genre id 1, sorted by rating in desc")
    public void whenFindMoviesByGenreIdAndRatingSortingOrderDesc_thenReturnListOfFourMoviesSortedByRatingDesc() {
        List<MovieDto> found = defaultMovieService.findByGenreId(1, RatingSortingOrder.desc);
        assertEquals(4, found.size());

        MovieDto actual = found.getFirst();

        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(Year.of(1999), actual.getYearOfRelease());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());

        assertEquals(1, found.get(1).getId());
    }

    @Test
    @DisplayName("Get list of four movies by genre id 1, sorted by price in asc")
    public void whenFindMoviesByGenreIdAndPriceSortingOrderAsc_thenReturnListOfFourMoviesSortedByPriceAsc() {
        List<MovieDto> found = defaultMovieService.findByGenreId(1, PriceSortingOrder.asc);
        assertEquals(4, found.size());

        MovieDto actual = found.getFirst();

        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(Year.of(1999), actual.getYearOfRelease());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());

        assertEquals(1, found.get(1).getId());
    }

    @Test
    @DisplayName("Get list of four movies by genre id 1, sorted by price in desc")
    public void whenFindMoviesByGenreIdAndPriceSortingOrderDesc_thenReturnListOfFourMoviesSortedByPriceDesc() {
        List<MovieDto> found = defaultMovieService.findByGenreId(1, PriceSortingOrder.desc);
        assertEquals(4, found.size());

        MovieDto actual = found.getFirst();

        assertEquals(3, actual.getId());
        assertEquals("Форест Гамп", actual.getNameUa());
        assertEquals("Forrest Gump", actual.getNameNative());
        assertEquals(Year.of(1994), actual.getYearOfRelease());
        assertEquals(8.6, actual.getRating());
        assertEquals(200.60, actual.getPrice());
        assertEquals("picturePath3", actual.getPicturePath());

        assertEquals(4, found.get(1).getId());
    }

    @Test
    public void testFindById_whenUserIsAvailable() {
        Movie expectedMovie = Movie.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(8.9)
                .price(140.45)
                .picturePath("picturePath1")
                .countries(List.of(Country.builder().id(1).name("США").build()))
                .genres(List.of(Genre.builder().id(1).name("драма").build(),
                        Genre.builder().id(2).name("кримінал").build()))
                .reviews(List.of(
                        Review.builder().id(1).user(User.builder().id(1).nickname("reviewUser1").build())
                                .description("reviewDescription1").build()))
                .build();

        MovieDetailsDto movieDetailsDto = MovieDetailsDto.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(Year.of(1994))
                .description("testDescription1")
                .rating(8.9)
                .price(140.45)
                .picturePath("picturePath1")
                .countries(List.of(CountryDto.builder().id(1).name("США").build()))
                .genres(List.of(GenreDto.builder().id(1).name("драма").build(),
                        GenreDto.builder().id(2).name("кримінал").build()))
                .reviews(List.of(
                        ReviewDto.builder().id(1).user(UserDto.builder().id(1).nickname("reviewUser1").build())
                                .description("reviewDescription1").build()))
                .build();

        when(movieRepository.findById(1)).thenReturn(Optional.of(expectedMovie));
        when(movieMapper.toMovieDetailsDto(expectedMovie)).thenReturn(movieDetailsDto);

        MovieDetailsDto movieDetailsActualsDto = defaultMovieService.findById(1);

        assertEquals(1, movieDetailsActualsDto.getId());
        assertEquals("Втеча з Шоушенка", movieDetailsActualsDto.getNameUa());
        assertEquals("The Shawshank Redemption", movieDetailsActualsDto.getNameNative());
        assertEquals(Year.of(1994), movieDetailsActualsDto.getYearOfRelease());
        assertEquals("testDescription1", movieDetailsActualsDto.getDescription());
        assertEquals(8.9, movieDetailsActualsDto.getRating());
        assertEquals(140.45, movieDetailsActualsDto.getPrice());
        assertEquals("picturePath1", movieDetailsActualsDto.getPicturePath());
        assertEquals(1, movieDetailsActualsDto.getCountries().getFirst().getId());
        assertEquals("США", movieDetailsActualsDto.getCountries().getFirst().getName());
        assertEquals(2, movieDetailsActualsDto.getGenres().size());
        assertEquals(1, movieDetailsActualsDto.getGenres().getFirst().getId());
        assertEquals("драма", movieDetailsActualsDto.getGenres().getFirst().getName());
        assertEquals(1, movieDetailsActualsDto.getReviews().size());
        assertEquals(1, movieDetailsActualsDto.getReviews().getFirst().getId());
        assertEquals("reviewDescription1", movieDetailsActualsDto.getReviews().getFirst().getDescription());
        assertEquals(1, movieDetailsActualsDto.getReviews().getFirst().getUser().getId());
        assertEquals("reviewUser1", movieDetailsActualsDto.getReviews().getFirst().getUser().getNickname());
    }

    @Test
    public void testFindById_whenUserIsNotPresent() {
        when(movieRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(MovieNotFoundException.class, () -> {
            defaultMovieService.findById(2);
        });
        assertTrue(exception.getMessage().contains("No such movie found"));
    }

    @Test
    public void testAdd() {
        AddMovieDto addMovieDto = AddMovieDto.builder()
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(1994)
                .description("testDescription1")
                .price(140.45)
                .picturePath("https://testpicturepath")
                .countries(List.of(AddCountryDto.builder().id(1).name("США").build(),
                        AddCountryDto.builder().id(2).name("Франція").build()))
                .genres(List.of(AddGenreDto.builder().id(1).name("драма").build(),
                        AddGenreDto.builder().id(2).name("пригоди").build()))
                .build();

        Movie movieToAdd = Movie.builder()
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(0.0)
                .price(140.45)
                .picturePath("https://testpicturepath")
                .countries(List.of(Country.builder().id(1).name("США").build(),
                        Country.builder().id(2).name("Франція").build()))
                .genres(List.of(Genre.builder().id(1).name("драма").build(),
                        Genre.builder().id(2).name("пригоди").build()))
                .build();

        Movie movieExpected = Movie.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(LocalDate.of(1994, 1, 1))
                .description("testDescription1")
                .rating(0.0)
                .price(140.45)
                .picturePath("https://testpicturepath")
                .countries(List.of(Country.builder().id(1).name("США").build(),
                        Country.builder().id(2).name("Франція").build()))
                .genres(List.of(Genre.builder().id(1).name("драма").build(),
                        Genre.builder().id(2).name("пригоди").build()))
                .build();

        MovieDetailsDto movieDetailsDtoExpected = MovieDetailsDto.builder()
                .id(1)
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(Year.of(1994))
                .description("testDescription1")
                .rating(0.0)
                .price(140.45)
                .picturePath("https://testpicturepath")
                .countries(List.of(CountryDto.builder().id(1).name("США").build(),
                        CountryDto.builder().id(2).name("Франція").build()))
                .genres(List.of(GenreDto.builder().id(1).name("драма").build(),
                        GenreDto.builder().id(2).name("пригоди").build()))
                .build();

        when(movieMapper.toMovie(addMovieDto)).thenReturn(movieToAdd);
        when(movieRepository.save(movieToAdd)).thenReturn(movieExpected);
        when(movieMapper.toMovieDetailsDto(movieExpected)).thenReturn(movieDetailsDtoExpected);

        MovieDetailsDto movieDetailsDtoActual = defaultMovieService.add(addMovieDto);

        assertEquals(1, movieDetailsDtoActual.getId());
        assertEquals("Втеча з Шоушенка", movieDetailsDtoActual.getNameUa());
        assertEquals("The Shawshank Redemption", movieDetailsDtoActual.getNameNative());
        assertEquals(Year.of(1994), movieDetailsDtoActual.getYearOfRelease());
        assertEquals("testDescription1", movieDetailsDtoActual.getDescription());
        assertEquals(0.0, movieDetailsDtoActual.getRating());
        assertEquals(140.45, movieDetailsDtoActual.getPrice());
        assertEquals("https://testpicturepath", movieDetailsDtoActual.getPicturePath());
        assertEquals(2, movieDetailsDtoActual.getCountries().size());
        assertEquals(1, movieDetailsDtoActual.getCountries().getFirst().getId());
        assertEquals("США", movieDetailsDtoActual.getCountries().getFirst().getName());
        assertEquals(2, movieDetailsDtoActual.getGenres().size());
        assertEquals(1, movieDetailsDtoActual.getGenres().getFirst().getId());
        assertEquals("драма", movieDetailsDtoActual.getGenres().getFirst().getName());
    }
}
