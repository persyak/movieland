package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;
import jakarta.annotation.Nonnull;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Nonnull
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<Genre> findAll();
}
