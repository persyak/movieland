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
public class RateDetailsDto {

    private Integer r030;
    private String txt;
    private Double rate;
    private String cc;
    private String exchangedate;
}
