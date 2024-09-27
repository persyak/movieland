package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
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

    //TODO: implement method order by price and rating
    @Override
    public List<Movie> findByGenreIdAndSortByPriceAndRating(int genreId, String priceSortingOrder, String ratingSortingOrder) {
        return List.of();
    }

    private TypedQuery<Movie> create(String priceSortingOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> movie = criteriaQuery.from(Movie.class);
        criteriaQuery.select(movie);

        List<Order> movieList = new ArrayList<>();
        if (priceSortingOrder.equals("asc")) {
            movieList.add(criteriaBuilder.asc(movie.get("price")));
        } else {
            movieList.add(criteriaBuilder.desc(movie.get("price")));
        }
        movieList.add(criteriaBuilder.desc(movie.get("rating")));

        criteriaQuery.orderBy(movieList);

        return entityManager.createQuery(criteriaQuery);
    }
}
