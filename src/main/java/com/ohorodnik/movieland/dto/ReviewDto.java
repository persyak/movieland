package com.ohorodnik.movieland.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ReviewDto {

    private Integer id;
    private UserDto user;
    private String description;
}
