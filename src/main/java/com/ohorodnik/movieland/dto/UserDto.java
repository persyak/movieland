package com.ohorodnik.movieland.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {
    private Integer id;
    private String nickname;
}
