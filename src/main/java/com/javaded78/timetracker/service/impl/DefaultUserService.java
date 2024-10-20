package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.user.UserDto;
import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.mapper.UserMapper;
import com.javaded78.timetracker.model.Role;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.repository.UserRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceService messageService;

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        check(userDto);
        User newUser = userMapper.toEntity(userDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        newUser.setRoles(roles);
        User user = userRepository.saveAndFlush(newUser);
        return userMapper.toDto(user);
    }

    private void check(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new IllegalStateException(messageService.generateMessage("error.account.already_exists", userDto.email()));
        }
    }

    @Override
    public PaginatedResponse<UserDto> getAll(Pageable pageable) {
        Page<UserDto> users = userRepository.findAll(pageable)
                .map(userMapper::toDto);
        return new PaginatedResponse<>(users);
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(getUserById(id));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.not_found", id))
                );
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = getUserById(id);
        existingUser.setFirstname(userDto.firstname());
        existingUser.setLastname(userDto.lastname());
        existingUser.setEmail(userDto.email());
        existingUser.setPassword(passwordEncoder.encode(userDto.password()));
        return userMapper.toDto(userRepository.saveAndFlush(existingUser));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.not_found", email))
                );
    }
}


