package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserDto register(UserDto userDto);

    PaginatedResponse<UserDto> getAll(Pageable pageable);

    UserDto getById(Long id);

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);

    UserDto getUserByEmail(String email);
}
