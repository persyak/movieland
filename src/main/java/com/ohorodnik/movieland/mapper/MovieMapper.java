package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "yearOfRelease", source = "yearOfRelease", qualifiedByName = "mapYearOfRelease")
    MovieDto toMovieDto(Movie movie);

    List<MovieDto> toMovieDtoList(List<Movie> movies);

    @Named("mapYearOfRelease")
    default Year mapYearOfRelease(LocalDate yearOfRelease) {
        return Year.from(yearOfRelease);
    }
}
