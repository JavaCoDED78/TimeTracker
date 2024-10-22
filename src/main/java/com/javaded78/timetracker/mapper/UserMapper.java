package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.user.UserCreateDto;
import com.javaded78.timetracker.dto.user.UserResponseDto;
import com.javaded78.timetracker.dto.user.UserUpdateDto;
import com.javaded78.timetracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserResponseDto> {

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User createdToEntity(UserCreateDto userCreateDto);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User updatedToEntity(UserUpdateDto userUpdateDto);
}
