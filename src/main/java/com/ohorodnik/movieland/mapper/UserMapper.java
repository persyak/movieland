package com.ohorodnik.movieland.mapper;

import com.ohorodnik.movieland.dto.UserDto;
import com.ohorodnik.movieland.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}
