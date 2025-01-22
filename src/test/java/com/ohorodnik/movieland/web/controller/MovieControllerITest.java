package com.ohorodnik.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser
public class MovieControllerITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movie-dataset.json";
    private static final String MOVIE_GENRE_DATASET = "datasets/movie/movie-genre-dataset.json";
    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";
    private static final String MOVIE_COUNTRY_DATASET = "datasets/movie/movie-country-dataset.json";
    private static final String COUNTRY_DATASET = "datasets/country/country-dataset.json";
    private static final String REVIEW_DATASET = "datasets/review/review-dataset.json";
    private static final String USER_DATASET = "datasets/user/user-dataset.json";
    private static final String RATING_DATASET = "datasets/rating/rating-dataset.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAll() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByRatingDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all-sorting-by-rating-desc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceAsc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all-sorting-by-price-asc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all-sorting-by-price-desc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceAscAndRatingDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=asc&ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all-price-asc-rating-desc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testFindAllSortedByPriceDescAndRatingDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=desc&ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-all-price-desc-rating-desc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = MOVIES_DATASET, skipCleaningFor = "flyway_schema_history")
    public void testGetThreeRandomMovies() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenreId() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-genre-id.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenreIdSortedByRatingDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/1?ratingSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-genre-id-sorting-rating-desc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenreIdSortedByPriceAsc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/1?priceSortingOrder=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-genre-id-sorting-price-asc.json")));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET}, skipCleaningFor = "flyway_schema_history")
    public void testFindByGenreIdSortedByPriceDesc() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/1?priceSortingOrder=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-genre-id-sorting-price-desc.json")));

        assertSelectCount(1);
    }

    @Test
    public void testFindAllWithBadSortedByRatingExpression() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?ratingSortingOrder=fail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertSelectCount(0);
    }

    @Test
    public void testFindAllWithBadSortedByPriceExpression() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=fail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertSelectCount(0);
    }

    @Test
    public void testFindAllWithBadSortedByPriceAndRatingExpressions() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies?priceSortingOrder=fail&ratingSortingOrder=fail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertSelectCount(0);
    }

    @Test
    public void testFindByGenreIdWithBadSortedByRatingExpression() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/1?ratingSortingOrder=fail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertSelectCount(0);
    }

    @Test
    public void testFindByGenreIdWithBadSortedByPriceExpression() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/genres/1?priceSortingOrder=fail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertSelectCount(0);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET,
            MOVIE_COUNTRY_DATASET, COUNTRY_DATASET, REVIEW_DATASET, USER_DATASET, RATING_DATASET},
            skipCleaningFor = "flyway_schema_history")
    public void testFindById() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-id.json")));

        assertSelectCount(6);
    }
}
