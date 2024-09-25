package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class Movie {

    @Id
    private int id;
    @NotBlank(message = "Please add item name")
    @Size(min = 3)
    private String nameUa;
    @NotBlank(message = "Please add item name")
    @Size(min = 3)
    private String nameNative;
    @Builder.Default
    private LocalDate yearOfRelease = LocalDate.now();
    private String description;
    @Positive(message = "Value should be above zero")
    private Double rating;
    @Positive(message = "Value should be above zero")
    private Double price;
    private String picturePath;
    @Positive(message = "Value should be above zero")
    private int votes;

    @ManyToMany
    @JoinTable(name = "movie_genre", schema = "movieland",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();
}
