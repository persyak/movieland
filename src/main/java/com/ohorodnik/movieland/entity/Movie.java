package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    private int id;
    private String nameUa;
    private String nameNative;
    private Date yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;
    private int votes;
}
