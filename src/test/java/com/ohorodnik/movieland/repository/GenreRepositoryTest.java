package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;
import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.utils.TestConfigurationToCountAllQueries;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfigurationToCountAllQueries.class)
public class GenreRepositoryTest extends BaseContainerImpl {

    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DataSet(value = GENRE_DATASET, disableConstraints = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllGenres() {

        SQLStatementCountValidator.reset();

        List<Genre> genresList = genreRepository.findAll();
        assertEquals(5, genresList.size());

        Genre actual = genresList.getFirst();
        assertEquals(1, actual.getId());
        assertEquals("genre1", actual.getName());

        assertSelectCount(6);
    }
}
