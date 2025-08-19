package com.ohorodnik.movieland.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDto {

    private Integer id;
    private String nameUa;
    private String nameNative;
    private Year yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;

    private List<CountryDto> countries;
    private List<GenreDto> genres;
    private List<ReviewDto> reviews;
}
