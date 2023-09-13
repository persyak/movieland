package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Movie;
import com.ohorodnik.movieland.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieRepository implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                "select * from movie", new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public List<Integer> getMovieIdsByGenreId(int genreId) {
        return null;
    }

    @Override
    public Iterable<Movie> getMoviesByMovieIds(List<Integer> movieIdsList) {
        return null;
    }
}
