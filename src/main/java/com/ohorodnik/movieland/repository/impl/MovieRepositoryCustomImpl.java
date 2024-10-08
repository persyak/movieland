package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryCustomImpl implements MovieRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Movie> findAndSortByPriceAndRating(String priceSortingOrder) {
        return create(priceSortingOrder).getResultList();
    }

    @Override
    public List<Movie> findByGenreIdAndSortByPriceAndRating(Integer genreId, String priceSortingOrder) {
        return create(genreId, priceSortingOrder).getResultList();
    }

    TypedQuery<Movie> create(String priceSortingOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> movie = criteriaQuery.from(Movie.class);

        criteriaQuery.select(movie);

        criteriaQuery.orderBy(sort(criteriaBuilder, movie, priceSortingOrder));

        return entityManager.createQuery(criteriaQuery);
    }

    TypedQuery<Movie> create(Integer genreId, String priceSortingOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> movie = criteriaQuery.from(Movie.class);

        criteriaQuery.where(criteriaBuilder.in(movie.get("id")).value(
                createSubquery(genreId, criteriaBuilder, criteriaQuery)));

        criteriaQuery.orderBy(sort(criteriaBuilder, movie, priceSortingOrder));

        return entityManager.createQuery(criteriaQuery);
    }

    private Subquery<Integer> createSubquery(Integer genreId, CriteriaBuilder criteriaBuilder, CriteriaQuery<Movie> criteriaQuery) {
        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<Movie> subqueryMovie = subquery.from(Movie.class);
        Join<Genre, Movie> subqueryGenre = subqueryMovie.join("genres");
        subquery.select(subqueryMovie.get("id")).where(
                criteriaBuilder.equal(subqueryGenre.get("id"), genreId));

        return subquery;
    }

    private List<Order> sort(CriteriaBuilder criteriaBuilder, Root<Movie> list, String priceSortingOrder) {
        List<Order> movieList = new ArrayList<>();
        if (priceSortingOrder.equals("asc")) {
            movieList.add(criteriaBuilder.asc(list.get("price")));
        } else {
            movieList.add(criteriaBuilder.desc(list.get("price")));
        }
        movieList.add(criteriaBuilder.desc(list.get("rating")));

        return movieList;
    }
}
