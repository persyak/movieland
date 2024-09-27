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

//TODO: I'm not sure it's ITest as it tests only one component - repository. Or is it possible to mock DB?

//TODO: To count SQL queries I had to use datasource, so I need something liek wrapper on it. https://vladmihalcea.com/how-to-detect-the-n-plus-one-query-problem-during-testing/ or https://vladmihalcea.com/how-to-detect-the-n-plus-one-query-problem-during-testing/
//TODO: I have moved @Import(TestConfigurationToCountAllQueries.class) to BaseCOntainerImpl class
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
