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

import java.time.LocalDate;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestConfigurationToCountAllQueries.class)
public class MovieControllerITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movies-dataset.json";
//    private static final String MOVIE_GENRE_DATASET = "datasets/movie/movie-genre-dataset.json";
//    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAll() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/movie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1994, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByRatingDesc() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/movie?rating=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1999, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceAsc() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/movie?price=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("5"))
                .andExpect(jsonPath("$[0].nameUa").value("1+1"))
                .andExpect(jsonPath("$[0].nameNative").value("Intouchables"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(2011, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.3"))
                .andExpect(jsonPath("$[0].price").value("120.0"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath5"));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceDesc() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/movie?price=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1994, 1, 1)
                        .toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetThreeRandomMovies() throws Exception {

        SQLStatementCountValidator.reset();

        mockMvc.perform(get("/api/v1/movie/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));

        assertSelectCount(1);
    }

//    @Test
//    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET}, disableConstraints = true,
//            skipCleaningFor = "flyway_schema_history")
//    public void testFindByGenreId() {
//
//        SQLStatementCountValidator.reset();
//
//        await().atMost(7000, TimeUnit.MILLISECONDS).untilAsserted(
//                () -> mockMvc.perform(get("/api/v1/movie/genre/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].id").value("2"))
//                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
//                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
//                .andExpect(jsonPath("$[0].yearOfRelease").value(LocalDate.of(1999, 1, 1)
//                        .toString()))
//                .andExpect(jsonPath("$[0].description").value("testDescription2"))
//                .andExpect(jsonPath("$[0].rating").value("9.0"))
//                .andExpect(jsonPath("$[0].price").value("134.67"))
//                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"))
//                .andExpect(jsonPath("$[0].votes").value("100"))
//        );
//
//        assertSelectCount(2);
//    }
}
