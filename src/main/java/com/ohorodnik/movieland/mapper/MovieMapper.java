package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.AddMovieDto;
import com.ohorodnik.movieland.dto.EditMovieDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.dto.MovieDto;
import com.ohorodnik.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class, CountryMapper.class, ReviewMapper.class})
public interface MovieMapper {

    @Mapping(target = "yearOfRelease", source = "yearOfRelease", qualifiedByName = "mapFromLocalDateToYear")
    MovieDto toMovieDto(Movie movie);

    List<MovieDto> toMovieDtoList(List<Movie> movies);

    @Mapping(target = "yearOfRelease", source = "yearOfRelease", qualifiedByName = "mapFromLocalDateToYear")
    MovieDetailsDto toMovieDetailsDto(Movie movie);

    @Mapping(target = "yearOfRelease", source = "yearOfRelease", qualifiedByName = "mapFromIntegerToYear")
    Movie toMovie(AddMovieDto addMovieDto);

    @Mapping(target = "id", ignore = true)
    Movie update(@MappingTarget Movie movie, EditMovieDto editMovieDto);

    @Named("mapFromLocalDateToYear")
    default Year mapFromLocalDateToYear(LocalDate yearOfRelease) {
        return Year.from(yearOfRelease);
    }

    @Named("mapFromIntegerToYear")
    default LocalDate mapFromIntegerToYear(Integer yearOfRelease) {
        return LocalDate.of(yearOfRelease, 1, 1);
    }
}
