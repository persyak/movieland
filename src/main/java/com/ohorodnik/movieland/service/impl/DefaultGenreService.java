package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.cache.GenreCache;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.mapper.GenreMapper;
import com.ohorodnik.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultGenreService implements GenreService {

    private final GenreCache genreCache;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreMapper.toGenreDtoList(genreCache.findAll());
    }

    @Override
    public List<GenreDto> findByGenreIdList(List<Integer> ids) {
        return genreMapper.toGenreDtoList(genreCache.findByIdList(ids));
    }
}
