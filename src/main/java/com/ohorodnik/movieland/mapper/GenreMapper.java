package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toGenreDto(Genre genre);

    List<GenreDto> toGenreDtoList(List<Genre> genres);
}
