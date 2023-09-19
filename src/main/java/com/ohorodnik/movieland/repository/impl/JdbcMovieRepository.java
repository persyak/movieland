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

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    RowMapper<Movie> rowMapper = (rs, rowNum) ->
//        new Movie(
//                rs.getInt("id"),
//                rs.getString("name_ua"),
//                rs.getString("name_native"),
//                rs.getDate("year_of_release"),
//                rs.getString("description"),
//                rs.getDouble("rating"),
//                rs.getDouble("price"),
//                rs.getString("picture_path"),
//                rs.getInt("votes"));

    @Override
    public Iterable<Movie> getAllMovies() {
        return jdbcTemplate.query(
                "select id, name_ua, name_native, year_of_release, description, rating, price, picture_path, votes from movie",
                new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public List<Integer> getMovieIdsByGenreId(int genreId) {
        return jdbcTemplate.queryForList(
                "select movie_id from movie_genre where genre_id=?", Integer.class, genreId);
    }

    @Override
    public Iterable<Movie> getMoviesByMovieIds(List<Integer> movieIdsList) {
        if (movieIdsList.isEmpty()) {
            return List.of();
        }
        SqlParameterSource movieIds = new MapSqlParameterSource("movieids", movieIdsList);
        return namedParameterJdbcTemplate.query(
                "select id, name_ua, name_native, year_of_release, description, rating, price, picture_path, votes from movie where id in (:movieids)",
                movieIds, new BeanPropertyRowMapper<>(Movie.class));
    }
}
