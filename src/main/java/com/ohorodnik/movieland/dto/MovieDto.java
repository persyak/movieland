package com.ohorodnik.movieland.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Year;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class MovieDto {

    private Integer id;
    private String nameUa;
    private String nameNative;
    private Year yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}
