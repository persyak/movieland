package com.ohorodnik.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser
public class MovieControllerITest extends BaseContainerImpl {

    private static final String MOVIES_DATASET = "datasets/movie/movie-dataset.json";
    private static final String MOVIE_GENRE_DATASET = "datasets/movie/movie-genre-dataset.json";
    private static final String GENRE_DATASET = "datasets/genre/genre-dataset.json";
    private static final String MOVIE_COUNTRY_DATASET = "datasets/movie/movie-country-dataset.json";
    private static final String COUNTRY_DATASET = "datasets/movie/country-dataset.json";
    private static final String REVIEW_DATASET = "datasets/movie/review-dataset.json";
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.9"))
                .andExpect(jsonPath("$[0].price").value("123.45"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath1"));

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
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1999).toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));

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
                .andExpect(jsonPath("$[0].id").value("5"))
                .andExpect(jsonPath("$[0].nameUa").value("1+1"))
                .andExpect(jsonPath("$[0].nameNative").value("Intouchables"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(2011).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.3"))
                .andExpect(jsonPath("$[0].price").value("120.0"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath5"));

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
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));

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
                .andExpect(jsonPath("$[0].id").value("5"))
                .andExpect(jsonPath("$[0].nameUa").value("1+1"))
                .andExpect(jsonPath("$[0].nameNative").value("Intouchables"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(2011).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.3"))
                .andExpect(jsonPath("$[0].price").value("120.0"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath5"));

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
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));

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
                .andExpect(jsonPath("$[0].id").value("4"))
                .andExpect(jsonPath("$[0].nameUa").value("Список Шиндлера"))
                .andExpect(jsonPath("$[0].nameNative").value("Schindler's List"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1993).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.7"))
                .andExpect(jsonPath("$[0].price").value("150.5"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath4"));

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
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1999).toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));

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
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].nameUa").value("Зелена миля"))
                .andExpect(jsonPath("$[0].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1999).toString()))
                .andExpect(jsonPath("$[0].rating").value("9.0"))
                .andExpect(jsonPath("$[0].price").value("134.67"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath2"));

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
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].nameUa").value("Форест Гамп"))
                .andExpect(jsonPath("$[0].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$[0].rating").value("8.6"))
                .andExpect(jsonPath("$[0].price").value("200.6"))
                .andExpect(jsonPath("$[0].picturePath").value("picturePath3"));

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
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nameUa").value("Втеча з Шоушенка"))
                .andExpect(jsonPath("$.nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.yearOfRelease").value(Year.of(1994).toString()))
                .andExpect(jsonPath("$.description").value("testDescription1"))
                .andExpect(jsonPath("$.rating").value("8.9"))
                .andExpect(jsonPath("$.price").value("123.45"))
                .andExpect(jsonPath("$.picturePath").value("picturePath1"))
                .andExpect(jsonPath("$.countries.[0].id").value("1"))
                .andExpect(jsonPath("$.countries.[0].name").value("США"))
                .andExpect(jsonPath("$.genres.size()").value(1))
                .andExpect(jsonPath("$.genres.[0].id").value("2"))
                .andExpect(jsonPath("$.genres.[0].name").value("genre2"))
                .andExpect(jsonPath("$.reviews.size()").value(2))
                .andExpect(jsonPath("$.reviews.[0].id").value("1"))
                .andExpect(jsonPath("$.reviews.[0].description").value("reviewDescription1"))
                .andExpect(jsonPath("$.reviews.[0].user.id").value("1"))
                .andExpect(jsonPath("$.reviews.[0].user.nickname").value("reviewUser1"));

        assertSelectCount(6);
    }
}
