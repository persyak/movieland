package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcGenreRepositoryTest extends BaseContainerImpl {

    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DataSet(value = GENRE_DATASET, disableConstraints = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllGenres(){

        List<Genre> genresList = genreRepository.getAllGenres();
        assertEquals(5, genresList.size());

        Genre actual = genresList.get(0);
        assertEquals(1, actual.getId());
        assertEquals("genre1", actual.getName());
    }
}
