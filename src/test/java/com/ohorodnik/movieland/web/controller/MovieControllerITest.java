package com.ohorodnik.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.cache.impl.GenreCacheImpl;
import com.ohorodnik.movieland.dto.AddCountryDto;
import com.ohorodnik.movieland.dto.AddGenreDto;
import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.EditCountryDto;
import com.ohorodnik.movieland.dto.EditGenreDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertUpdateCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser
//Below annotation is to close context after each method to avoid cached data.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieControllerITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movie-dataset.json";
    private static final String MOVIE_ADDED_DATASET = "datasets/movie/movie-added-dataset.json";
    private static final String MOVIE_GENRE_DATASET = "datasets/movie/movie-genre-dataset.json";
    private static final String MOVIE_GENRE_TO_EDIT_DATASET = "datasets/movie/movie-genre-to-edit-dataset.json";
    private static final String MOVIE_GENRE_ADDED_DATASET = "datasets/movie/movie-genre-added-dataset.json";
    private static final String MOVIE_GENRE_EDITED_DATASET = "datasets/movie/movie-genre-edited-dataset.json";
    private static final String MOVIE_TO_EDIT_DATASET = "datasets/movie/movie-to-edit-dataset.json";
    private static final String MOVIE_EDITED_DATASET = "datasets/movie/movie-edited-dataset.json";
    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";
    private static final String MOVIE_COUNTRY_DATASET = "datasets/movie/movie-country-dataset.json";
    private static final String MOVIE_COUNTRY_ADDED_DATASET = "datasets/movie/movie-country-added-dataset.json";
    private static final String MOVIE_COUNTRY_TO_EDIT_DATASET = "datasets/movie/movie-country-to-edit-dataset.json";
    private static final String MOVIE_COUNTRY_EDITED_DATASET = "datasets/movie/movie-country-edited-dataset.json";
    private static final String COUNTRY_DATASET = "datasets/country/country-dataset.json";
    private static final String REVIEW_DATASET = "datasets/review/review-dataset.json";
    private static final String USER_DATASET = "datasets/user/user-dataset.json";
    private static final String RATING_DATASET = "datasets/rating/rating-dataset.json";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreCacheImpl genreCache;

    @Test
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = MOVIES_DATASET, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetThreeRandomMovies() throws Exception {

        reset();

        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));

        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET},
            cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET},
            cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET},
            cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, GENRE_DATASET},
            cleanBefore = true, skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = {MOVIES_DATASET, MOVIE_GENRE_DATASET, MOVIE_COUNTRY_DATASET, GENRE_DATASET,
            COUNTRY_DATASET, REVIEW_DATASET, USER_DATASET, RATING_DATASET},
            cleanBefore = true,
            skipCleaningFor = "flyway_schema_history")
    public void testFindById() throws Exception {

        genreCache.initCache();

        reset();

        mockMvc.perform(get("/api/v1/movies/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/movie/find-by-id.json")));

//I'm wonderin g why do we have here only 1 SQL statement recorded. my assumption is that it's because
//we query genres, countries and reviews in future tasks in separate threads, that are not caught
// by SQL statements count.
        assertSelectCount(1);
    }

    @Test
    @DataSet(value = {GENRE_DATASET, COUNTRY_DATASET}, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = {GENRE_DATASET, COUNTRY_DATASET, MOVIE_ADDED_DATASET, MOVIE_COUNTRY_ADDED_DATASET,
            MOVIE_GENRE_ADDED_DATASET})
    @WithMockUser(authorities = "A")
    public void testAdd() throws Exception {

        reset();

        mockMvc.perform(post("/api/v1/movies/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAddMovieDto())))
                .andExpect(status().isCreated()).andExpect(content()
                        .json(getResponseAsString("responses/movie/add-movie.json")));

        assertSelectCount(1);
        assertInsertCount(5);

    }

    @Test
    @DataSet(value = {GENRE_DATASET, COUNTRY_DATASET, MOVIE_TO_EDIT_DATASET, MOVIE_COUNTRY_TO_EDIT_DATASET,
            MOVIE_GENRE_TO_EDIT_DATASET}, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = {GENRE_DATASET, COUNTRY_DATASET, MOVIE_EDITED_DATASET, MOVIE_COUNTRY_EDITED_DATASET,
            MOVIE_GENRE_EDITED_DATASET})
    @WithMockUser(authorities = "A")
    public void whenMovieIsAvailable_thenEditMovie() throws Exception {

        reset();

        mockMvc.perform(put("/api/v1/movies/movie/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEditMovieDto())))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString("responses/movie/edit-movie.json")));

        assertSelectCount(4);
        assertInsertCount(4);
        assertUpdateCount(1);
    }

    @Test
    @DataSet(value = {GENRE_DATASET, COUNTRY_DATASET, MOVIE_TO_EDIT_DATASET, MOVIE_COUNTRY_TO_EDIT_DATASET,
            MOVIE_GENRE_TO_EDIT_DATASET}, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = {GENRE_DATASET, COUNTRY_DATASET, MOVIE_TO_EDIT_DATASET, MOVIE_COUNTRY_TO_EDIT_DATASET,
            MOVIE_GENRE_TO_EDIT_DATASET})
    @WithMockUser(authorities = "A")
    public void whenMovieIsNotAvailable_thenThrowMovieNotFoundExceptionWhenEditMovie() throws Exception {

        reset();

        mockMvc.perform(put("/api/v1/movies/movie/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEditMovieDto())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No such movie found"));

        assertSelectCount(1);
    }

    private AddMovieDto createAddMovieDto() {
        return AddMovieDto.builder()
                .nameUa("Втеча з Шоушенка")
                .nameNative("The Shawshank Redemption")
                .yearOfRelease(1994)
                .description("testDescription1")
                .price(140.45)
                .picturePath("https://testpicturepath")
                .countries(List.of(AddCountryDto.builder().id(1).name("США").build(),
                        AddCountryDto.builder().id(2).name("Франція").build()))
                .genres(List.of(AddGenreDto.builder().id(1).name("genre1").build(),
                        AddGenreDto.builder().id(2).name("genre2").build()))
                .build();
    }

    private EditMovieDto createEditMovieDto() {
        return EditMovieDto.builder()
                .nameUa("Нова Втеча з Шоушенка")
                .nameNative("The New Shawshank Redemption")
                .picturePath("https://testpicturepathNew")
                .countries(List.of(EditCountryDto.builder().id(3).name("Великобританія").build(),
                        EditCountryDto.builder().id(4).name("Італія").build()))
                .genres(List.of(EditGenreDto.builder().id(3).name("genre3").build(),
                        EditGenreDto.builder().id(4).name("genre4").build()))
                .build();
    }
}
