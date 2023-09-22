package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieRepository implements MovieRepository {
    private final String GET_ALL_SQL =
            "select id, name_ua, name_native, year_of_release, description, rating, price, picture_path, votes from movie";
    private final String GET_MOVIE_ID_BY_GENRE_ID_SQL = "select movie_id from movie_genre where genre_id=?";
    private final String GET_MOVIES_BY_MOVIE_IDS_SQL =
            "select id, name_ua, name_native, year_of_release, description, rating, price, picture_path, votes from movie where id in (:movieids)";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Movie> getAllMovies() {
        return jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public List<Integer> getMovieIdsByGenreId(int genreId) {
        return jdbcTemplate.queryForList(GET_MOVIE_ID_BY_GENRE_ID_SQL, Integer.class, genreId);
    }

    @Override
    public List<Movie> getMoviesByMovieIds(List<Integer> movieIdsList) {
        if (movieIdsList.isEmpty()) {
            return List.of();
        }
        SqlParameterSource movieIds = new MapSqlParameterSource("movieids", movieIdsList);
        return namedParameterJdbcTemplate.query(GET_MOVIES_BY_MOVIE_IDS_SQL,
                movieIds,
                new BeanPropertyRowMapper<>(Movie.class));
    }
}
