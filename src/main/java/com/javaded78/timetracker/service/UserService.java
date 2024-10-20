package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto register(UserDto userDto);

    Page<UserDto> getAll(Pageable pageable);

    UserDto getById(Long id);

    UserDto update(UserDto userDto);

    void delete(Long id);

    boolean existsByEmail(String email);
}
