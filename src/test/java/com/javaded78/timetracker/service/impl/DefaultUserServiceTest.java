package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.config.TestConfig;
import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Role;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class DefaultUserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DefaultUserService userService;

    @Test
    void getById() {
        //given
        Long id = 1L;
        User user = new User();
        user.setId(id);
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        //when
        User testUser = userService.getById(id);
        //then
        Mockito.verify(userRepository).findById(id);
        assertEquals(user, testUser);
    }

    @Test
    void getByNotExistingId() {
        //given
        Long id = 1L;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
        //when

        //then
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    void getByUsername() {
        //given
        String username = "username@gmail.com";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        //when
        User testUser = userService.getByUsername(username);
        //then
        Mockito.verify(userRepository).findByUsername(username);
        assertEquals(user, testUser);
    }

    @Test
    void getByNotExistingUsername() {
        //given
        String username = "username@gmail.com";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void update() {
        //given
        Long id = 1L;
        String password = "password";
        String username = "username@gmail.com";
        String firstname = "firstname";
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setUsername(username);
        user.setFirstname(firstname);
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        //when
        User updated = userService.update(user);
        Mockito.verify(passwordEncoder).encode(password);
        Mockito.verify(userRepository).save(user);
        assertEquals(user.getUsername(), updated.getUsername());
        assertEquals(user.getFirstname(), updated.getFirstname());
    }

    @Test
    void isTaskOwner() {
        //given
        Long userId = 1L;
        Long taskId = 2L;
        Mockito.when(userRepository.isTaskOwner(userId, taskId))
                .thenReturn(true);
        //when
        boolean isOwner = userService.isTaskOwner(userId, taskId);
        //then
        Mockito.verify(userRepository).isTaskOwner(userId, taskId);
        assertTrue(isOwner);
    }

    @Test
    void register() {
        //given
        String username = "username@gmail.com";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        //when
        User testUser = userService.register(user);
        //then
        Mockito.verify(userRepository).save(user);
        assertEquals(Set.of(Role.ROLE_USER), testUser.getRoles());
        assertEquals("encodedPassword",
                testUser.getPassword());
    }

    @Test
    void registerWithExistingUsername() {
        //given
        String username = "username@gmail.com";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        //when
        //then
        assertThrows(IllegalStateException.class,
                () -> userService.register(user));
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void isTaskOwnerWithFalse() {
        //given
        Long userId = 1L;
        Long taskId = 1L;
        Mockito.when(userRepository.isTaskOwner(userId, taskId))
                .thenReturn(false);
        //when
        boolean isOwner = userService.isTaskOwner(userId, taskId);
        //then
        Mockito.verify(userRepository).isTaskOwner(userId, taskId);
        assertFalse(isOwner);
    }

    @Test
    void getTaskAuthor() {
        //given
        Long taskId = 1L;
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Mockito.when(userRepository.findTaskAuthor(taskId))
                .thenReturn(Optional.of(user));
        //when
        User author = userService.getTaskAuthor(taskId);
        //then
        Mockito.verify(userRepository).findTaskAuthor(taskId);
        assertEquals(user, author);
    }

    @Test
    void getNotExistingTaskAuthor() {
        //given
        Long taskId = 1L;
        Mockito.when(userRepository.findTaskAuthor(taskId))
                .thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () ->
                userService.getTaskAuthor(taskId));
        Mockito.verify(userRepository).findTaskAuthor(taskId);
    }

    @Test
    void delete() {
        //given
        Long id = 1L;
        //when
        userService.delete(id);
        //then
        Mockito.verify(userRepository).deleteById(id);
    }
}
