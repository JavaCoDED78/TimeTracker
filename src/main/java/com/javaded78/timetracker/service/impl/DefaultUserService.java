package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
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
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceService messageSourceService;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.id.not_found", id))
                );
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.username.not_found", username))
                );
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
        return user;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    public User getTaskAuthor(Long taskId) {
        return userRepository.findTaskAuthor(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSourceService.generateMessage("error.entity.id.not_found", taskId))
                );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}


