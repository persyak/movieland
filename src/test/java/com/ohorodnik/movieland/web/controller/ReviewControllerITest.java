package com.ohorodnik.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.ohorodnik.movieland.BaseContainerImpl;
import com.ohorodnik.movieland.dto.AddReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReviewControllerITest extends BaseContainerImpl {

    private static final String USER_DATASET = "datasets/user/user-dataset.json";
    private static final String MOVIE_DATASET = "datasets/movie/movie-dataset.json";
    private static final String MOVIE_WITHOUT_REQUIRED_DATASET = "datasets/movie/movie-without-required-dataset.json";
    private static final String ADDED_REVIEW_DATASET = "datasets/review/added-review-dataset.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "U", username = "user1.email@example.com")
    @DataSet(value = {USER_DATASET, MOVIE_DATASET}, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = {USER_DATASET, MOVIE_DATASET, ADDED_REVIEW_DATASET})
    public void whenUserExistsAndMovieExistsAndReviewIsNotAdded_thenAddReview() throws Exception {

        reset();

        mockMvc.perform(post("/api/v1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAddReviewDto())))
                .andExpect(status().isOk()).andExpect(content()
                        .json(getResponseAsString("responses/review/added-review.json")));

        assertSelectCount(4);
        assertInsertCount(1);
    }

    @Test
    @WithMockUser(authorities = "U", username = "user1.email@example.com")
    @DataSet(value = {USER_DATASET, MOVIE_DATASET, ADDED_REVIEW_DATASET}, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = {USER_DATASET, MOVIE_DATASET})
    public void ifReviewExists_thenThrowReviewExistsException() throws Exception {

        reset();

        mockMvc.perform(post("/api/v1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAddReviewDto())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("User reviewUser1 added review for movie 1 already"));

        assertSelectCount(2);
    }

    @Test
    @WithMockUser(authorities = "U", username = "user1.email@example.com")
    @DataSet(value = {USER_DATASET, MOVIE_WITHOUT_REQUIRED_DATASET}, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    public void ifMovieDoesNotExist_thenThrowMovieNotFoundException() throws Exception {

        reset();

        mockMvc.perform(post("/api/v1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAddReviewDto())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No such movie found"));

        assertSelectCount(3);
    }

    private AddReviewDto createAddReviewDto() {
        return AddReviewDto.builder()
                .movieId(1)
                .description("reviewDescription1")
                .build();
    }
}
