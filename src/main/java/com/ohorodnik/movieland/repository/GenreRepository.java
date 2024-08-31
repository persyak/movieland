package com.ohorodnik.movieland.repository;

import com.ohorodnik.movieland.entity.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
