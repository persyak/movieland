package com.ohorodnik.movieland.dto;

import lombok.*;

import java.time.Year;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private int id;
    private String nameUa;
    private String nameNative;
    private Year yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;
    private int votes;
}
