package com.ohorodnik.movieland.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class AddCountryDto {

    @NotNull
    private Integer id;
    @NotBlank(message = "Please add country name")
    private String name;
}
