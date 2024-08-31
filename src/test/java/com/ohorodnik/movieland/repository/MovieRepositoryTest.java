package com.ohorodnik.movieland.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.utils.TestConfigurationToCountAllQueries;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfigurationToCountAllQueries.class)
public class MovieRepositoryTest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movies-dataset.json";

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies() {

        SQLStatementCountValidator.reset();

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
}
