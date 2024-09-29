package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Movie;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MovieRepositoryCustomImplTest {

    @Autowired
    private MovieRepositoryCustomImpl movieRepositoryCustomImpl;

    @Test
    public void testCreateQueryPriceAsc(){
        CriteriaQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create("asc");

        assertEquals(0, criteriaQuery.getParameters().size());
        assertEquals(Movie.class, criteriaQuery.getResultType());

        List<Order> list = criteriaQuery.getOrderList();
        assertTrue(list.getFirst().isAscending());
        assertFalse(list.getLast().isAscending());
    }

    @Test
    public void testCreateQueryPriceDesc(){
        CriteriaQuery<Movie> criteriaQuery = movieRepositoryCustomImpl.create("desc");

        assertEquals(0, criteriaQuery.getParameters().size());
        assertEquals(Movie.class, criteriaQuery.getResultType());

        List<Order> list = criteriaQuery.getOrderList();
        assertFalse(list.getFirst().isAscending());
        assertFalse(list.getLast().isAscending());
    }
}
