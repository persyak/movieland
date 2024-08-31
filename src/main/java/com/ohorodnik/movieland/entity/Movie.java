package com.ohorodnik.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movie", schema = "movieland")
@JsonIgnoreProperties(value = "genres")
public class Movie {

    @Id
    private int id;
    private String nameUa;
    private String nameNative;
    private LocalDate yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;
    private int votes;

    @ManyToMany
    @JoinTable(name = "movie_genre", schema = "movieland",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();
}
