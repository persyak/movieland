package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Movie;

import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MovieRepositoryCustomImplTest {

    @Autowired
    private MovieRepositoryCustomImpl movieRepositoryCustomImpl;

    //TODO: for some reasons criteriaQuery.unwrap(Query.class).getQueryString() does not return simple SQL, but it should
    @Test
    public void testCreateQueryPriceAscForFind() {
        TypedQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create("asc");
        assertEquals("<criteria>", criteriaQuery.unwrap(Query.class).getQueryString());

    }

    @Test
    public void testCreateQueryPriceDescforFind() {
        TypedQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create("desc");
        assertEquals("<criteria>", criteriaQuery.unwrap(Query.class).getQueryString());
    }

    @Test
    public void testCreateQueryPriceAscForFindForFindByGenreId() {
        TypedQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create(1, "asc");
        assertEquals("<criteria>", criteriaQuery.unwrap(Query.class).getQueryString());
    }

    @Test
    public void testCreateQueryPriceDescForFindForFindByGenreId() {
        TypedQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create(1, "desc");
        assertEquals("<criteria>", criteriaQuery.unwrap(Query.class).getQueryString());
    }
}
