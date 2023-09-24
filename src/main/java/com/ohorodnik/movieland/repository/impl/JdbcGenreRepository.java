package com.ohorodnik.movieland.repository.impl;

import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    private final String GET_ALL_SQL = "select id, name from movieland.genre";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(Genre.class));
    }
}
