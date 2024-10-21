package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.user.UserDto;
import com.javaded78.timetracker.model.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto register(UserDto userDto);

    PaginatedResponse<UserDto> getAll(Pageable pageable);

    UserDto getById(Long id);

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);

    UserDto getUserDtoByEmail(String email);

    User getUserByEmail(String email);

}
