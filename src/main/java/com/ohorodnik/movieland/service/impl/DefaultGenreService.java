package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.entity.Genre;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.repository.GenreRepository;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultGenreService implements GenreService {

    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() {
        return genreMapper.toGenreDtoList(genreRepository.findAll());
    }

    @Override
    public List<GenreDto> findByGenreIdList(List<Integer> ids) {
        List <Genre> genres = genreRepository.findAll();
        return genreMapper.toGenreDtoList(genres.stream().filter(genre -> ids.contains(genre.getId())).toList());
    }
}
