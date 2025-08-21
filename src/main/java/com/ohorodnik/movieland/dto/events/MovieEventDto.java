package com.ohorodnik.movieland.dto.events;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.utils.enums.MovieEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MovieEventDto {

    private Integer movieEventId;
    private MovieEventType movieEventType;
    private MovieDetailsDto movieDetailsDto;
}
