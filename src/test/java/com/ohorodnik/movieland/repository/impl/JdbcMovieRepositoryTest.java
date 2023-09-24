package com.ohorodnik.movieland.repository.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import com.ohorodnik.movieland.utils.TestConfigurationToCountAllQueries;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestConfigurationToCountAllQueries.class)
public class JdbcMovieRepositoryTest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movies-dataset.json";
    private static final String MOVIE_GENRE_DATASET = "datasets/movie/movie-genre-dataset.json";

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies() {

        List<Movie> moviesList = movieRepository.getAllMovies();

        assertEquals(5, moviesList.size());

        Movie actual = moviesList.get(0);
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
    @DataSet(value = MOVIE_GENRE_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetMovieIdsByGenreId() {

        List<Integer> movieIdsList = movieRepository.getMovieIdsByGenreId(1);

        assertEquals(2, movieIdsList.size());
        assertEquals(2, movieIdsList.get(0));
        assertEquals(3, movieIdsList.get(1));
    }

    @Test
    @DataSet(value = MOVIE_GENRE_DATASET, skipCleaningFor = "flyway_schema_history")
    @DisplayName("Return empty list when requesting movie ids of non-existed genre id")
    public void whenNonExistedGenreIdProvided_thenReturnEmptyList() {
        assertTrue(movieRepository.getMovieIdsByGenreId(4).isEmpty());
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByMovieIds() {

        SQLStatementCountValidator.reset();

        List<Movie> moviesList = movieRepository.getMoviesByMovieIds(List.of(2, 3));

        assertEquals(2, moviesList.size());

        Movie actual = moviesList.get(0);
        assertEquals(2, actual.getId());
        assertEquals("Зелена миля", actual.getNameUa());
        assertEquals("The Green Mile", actual.getNameNative());
        assertEquals(LocalDate.of(1999, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription2", actual.getDescription());
        assertEquals(9.0, actual.getRating());
        assertEquals(134.67, actual.getPrice());
        assertEquals("picturePath2", actual.getPicturePath());
        assertEquals(100, actual.getVotes());

        assertSelectCount(1);
    }

    @Test
    @DisplayName("Return empty list when empty list of movie ids provided")
    public void whenEmptyMovieIdsListProvided_thenReturnEmptyList() {

        SQLStatementCountValidator.reset();

        assertTrue(movieRepository.getMoviesByMovieIds(List.of()).isEmpty());

        assertSelectCount(0);
    }
}
