package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Role;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.repository.UserRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceService messageSourceService;

    @Override
    public Page<User> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        log.info("Users with pageable: {} found", pageable);
        return users;
    }

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.id.not_found", id))
                );
        log.info("User with id: {} found", id);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.username.not_found", username))
                );
        log.info("User with username: {} found", username);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        User existing = getById(user.getId());
        existing.setFirstname(user.getFirstname());
        existing.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User with id: {} updated", user.getId());
        return user;
    }

    @Override
    @Transactional
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException(
                    messageSourceService.generateMessage("error.account.already_exists", user.getUsername())
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        log.info("User with id: {} created", user.getId());
        return user;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    public User getTaskAuthor(Long taskId) {
        User user = userRepository.findTaskAuthor(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.id.not_found", taskId))
                );
        log.info("User with task id: {} found", taskId);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }
}
