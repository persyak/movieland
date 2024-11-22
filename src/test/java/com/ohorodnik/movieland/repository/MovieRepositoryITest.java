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

public class MovieRepositoryITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movie-dataset.json";
    private static final String MOVIES_GENRES_DATASET = "datasets/movie/movie-genre-dataset.json";

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAll() {
        reset();

        List<Movie> found = movieRepository.findAll();

        assertEquals(5, found.size());

        Movie actual = found.getFirst();
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

        List<Movie> found = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.asc.toString()), "price"));

        Movie actual = found.getFirst();
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

        List<Movie> found = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(PriceSortingOrder.desc.toString()), "price"));

        Movie actual = found.getFirst();
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

        List<Movie> found = movieRepository.findAll(
                Sort.by(Sort.Direction.fromString(RatingSortingOrder.desc.toString()), "rating"));

        Movie actual = found.getFirst();
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
    @DataSet(value = {MOVIES_DATASET, MOVIES_GENRES_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenresId() {
        reset();

        List<Movie> found = movieRepository.findByGenres_Id(1);

        assertEquals(2, found.size());
        Movie actual = found.getFirst();
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
    @DataSet(value = {MOVIES_DATASET, MOVIES_GENRES_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenresId_WithRatingSortingOrderDesc() {
        reset();

        List<Movie> found = movieRepository.findByGenres_Id(
                1, Sort.by(Sort.Direction.fromString(RatingSortingOrder.desc.toString()), "rating"));

        assertEquals(2, found.size());
        Movie actual = found.getFirst();
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
    @DataSet(value = {MOVIES_DATASET, MOVIES_GENRES_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenresId_WithPriceOrderAsc() {
        reset();

        List<Movie> found = movieRepository.findByGenres_Id(
                3, Sort.by(Sort.Direction.fromString(PriceSortingOrder.asc.toString()), "price"));

        assertEquals(2, found.size());
        Movie actual = found.getFirst();
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
    @DataSet(value = {MOVIES_DATASET, MOVIES_GENRES_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenresId_WithPriceOrderDesc() {
        reset();

        List<Movie> found = movieRepository.findByGenres_Id(
                3, Sort.by(Sort.Direction.fromString(PriceSortingOrder.desc.toString()), "price"));

        assertEquals(2, found.size());
        Movie actual = found.getFirst();
        assertEquals(4, actual.getId());
        assertEquals("Список Шиндлера", actual.getNameUa());
        assertEquals("Schindler's List", actual.getNameNative());
        assertEquals(LocalDate.of(1993, 1, 1), actual.getYearOfRelease());
        assertEquals("testDescription4", actual.getDescription());
        assertEquals(8.7, actual.getRating());
        assertEquals(150.50, actual.getPrice());
        assertEquals("picturePath4", actual.getPicturePath());
        assertEquals(100, actual.getVotes());

        assertSelectCount(1);
    }
}
