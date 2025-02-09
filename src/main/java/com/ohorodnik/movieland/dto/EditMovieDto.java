package com.ohorodnik.movieland.dto;

import jakarta.validation.constraints.NotBlank;
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
public class EditMovieDto {

    @NotBlank(message = "Please add movie UA name")
    @Size(min = 3)
    private String nameUa;
    @NotBlank(message = "Please add movie native name")
    @Size(min = 3)
    private String nameNative;
    @NotBlank
    @URL(protocol = "https")
    private String picturePath;
    private List<EditCountryDto> countries;
    private List<EditGenreDto> genres;
}
