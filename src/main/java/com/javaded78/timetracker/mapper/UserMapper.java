package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.user.UserDto;
import com.javaded78.timetracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

    @Override
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "projects", ignore = true)
    User toEntity(UserDto dto);

}
