package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toMovieDto(Movie movie);

    List<MovieDto> toMovieDtoList(List<Movie> movies);
}
