package com.ohorodnik.movieland.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "movie", schema = "movieland")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence_generator")
    @SequenceGenerator(name = "movie_sequence_generator", sequenceName = "movie_id_seq", schema = "movieland", allocationSize = 1)
    private Integer id;
    @NotBlank(message = "Please add movie UA name")
    @Size(min = 3)
    private String nameUa;
    @NotBlank(message = "Please add movie native name")
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
    @JoinTable(name = "movie_country", schema = "movieland",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    @Builder.Default
    private List<Country> countries = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie_genre", schema = "movieland",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setMovie(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setMovie(null);
    }
}
