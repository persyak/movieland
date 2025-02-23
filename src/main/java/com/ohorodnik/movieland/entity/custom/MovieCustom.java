package com.ohorodnik.movieland.entity.custom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie", schema = "movieland")
public class MovieCustom {

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
    @NotNull
    private Double rating;
    @Positive(message = "Value should be above zero")
    private Double price;
    private String picturePath;
    private int votes;
}
