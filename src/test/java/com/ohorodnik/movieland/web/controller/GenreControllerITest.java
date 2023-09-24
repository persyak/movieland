package com.ohorodnik.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.utils.TestConfigurationToCountAllQueries;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestConfigurationToCountAllQueries.class)
public class GenreControllerITest extends BaseContainerImpl {

    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = GENRE_DATASET, disableConstraints = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("genre1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("genre2"));

        assertSelectCount(1);

        mockMvc.perform(get("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("genre1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("genre2"));

        assertSelectCount(1);
    }

}
