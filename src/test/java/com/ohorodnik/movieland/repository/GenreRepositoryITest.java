package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;
import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenreRepositoryITest extends BaseContainerImpl {

    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DataSet(value = GENRE_DATASET, disableConstraints = true, skipCleaningFor = "flyway_schema_history")
    public void testFindAll() {

        reset();

        List<Genre> genresList = genreRepository.findAll();
        assertEquals(5, genresList.size());

        Genre actual = genresList.getFirst();
        assertEquals(1, actual.getId());
        assertEquals("genre1", actual.getName());

        assertSelectCount(1);
    }
}
