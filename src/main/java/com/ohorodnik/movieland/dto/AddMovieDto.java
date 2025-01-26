package com.ohorodnik.movieland.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class AddMovieDto {

    @NotBlank(message = "Please add movie UA name")
    @Size(min = 3)
    private String nameUa;
    @NotBlank(message = "Please add movie native name")
    @Size(min = 3)
    private String nameNative;
    @NotNull
    private Integer yearOfRelease;
    private String description;
    @Positive(message = "Value should be above zero")
    private Double price;
    @NotBlank
    @URL(protocol = "https")
    private String picturePath;
    private List<AddCountryDto> countries;
    private List<AddGenreDto> genres;
}
