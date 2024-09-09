package com.ohorodnik.movieland.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class MovieDto {

    private int id;
    private String nameUa;
    private String nameNative;
    private LocalDate yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}
