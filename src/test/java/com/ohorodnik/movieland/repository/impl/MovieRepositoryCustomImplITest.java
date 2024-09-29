package com.ohorodnik.movieland.repository.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieRepositoryCustomImplITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movies-dataset.json";

    @Autowired
    private MovieRepositoryCustomImpl movieRepositoryCustomImpl;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testDindAndSortByPriceAndRatingWhenPriceIsAsc() {
        reset();

        List<Movie> moviesList = movieRepositoryCustomImpl.findAndSortByPriceAndRating("asc");

        assertEquals(5, moviesList.size());

        Movie actual = moviesList.getFirst();
        assertEquals(5, actual.getId());
        assertEquals("1+1", actual.getNameUa());
        assertEquals("Intouchables", actual.getNameNative());
        assertEquals(LocalDate.of(2011, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription5", actual.getDescription());
        assertEquals(8.3, actual.getRating());
        assertEquals(120.00, actual.getPrice());
        assertEquals("picturePath5", actual.getPicturePath());
        assertEquals(100, actual.getVotes());

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testDindAndSortByPriceAndRatingWhenPriceIsDesc() {
        reset();

        List<Movie> moviesList = movieRepositoryCustomImpl.findAndSortByPriceAndRating("desc");

        assertEquals(5, moviesList.size());

        Movie actual = moviesList.getFirst();
        assertEquals(3, actual.getId());
        assertEquals("Форест Гамп", actual.getNameUa());
        assertEquals("Forrest Gump", actual.getNameNative());
        assertEquals(LocalDate.of(1994, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription3", actual.getDescription());
        assertEquals(8.6, actual.getRating());
        assertEquals(200.60, actual.getPrice());
        assertEquals("picturePath3", actual.getPicturePath());
        assertEquals(100, actual.getVotes());

        assertSelectCount(1);
    }
}
