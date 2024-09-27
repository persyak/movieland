package com.ohorodnik.movieland.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.utils.enums.PriceSortingOrder;
import com.ohorodnik.movieland.utils.enums.RatingSortingOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieRepositoryTest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movies-dataset.json";

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAll() {
        reset();

        List<Movie> moviesList = movieRepository.findAll();

        assertEquals(5, moviesList.size());

        Movie actual = moviesList.getFirst();
        assertEquals(1, actual.getId());
        assertEquals("Втеча з Шоушенка", actual.getNameUa());
        assertEquals("The Shawshank Redemption", actual.getNameNative());
        assertEquals(LocalDate.of(1994, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription1", actual.getDescription());
        assertEquals(8.9, actual.getRating());
        assertEquals(123.45, actual.getPrice());
        assertEquals("picturePath1", actual.getPicturePath());
        assertEquals(100, actual.getVotes());

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllWithPriceSortingOrderAsc() {
        reset();

        List<Movie> moviesList = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.asc.toString()), "price"));

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
    public void testFindAllWithPriceSortingOrderDesc() {
        reset();

        List<Movie> moviesList = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.desc.toString()), "price"));

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

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllWithRatingSortingOrderDesc() {
        reset();

        List<Movie> moviesList = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(RatingSortingOrder.desc.toString()), "rating"));

        Movie actual = moviesList.getFirst();
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
}
