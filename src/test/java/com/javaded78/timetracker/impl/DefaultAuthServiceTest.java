package com.javaded78.timetracker.impl;

import com.javaded78.timetracker.config.TestConfig;
import com.javaded78.timetracker.dto.auth.JwtRequest;
import com.javaded78.timetracker.dto.auth.JwtResponse;
import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Role;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.repository.TaskRepository;
import com.javaded78.timetracker.repository.UserRepository;
import com.javaded78.timetracker.security.JwtTokenProvider;
import com.javaded78.timetracker.service.impl.DefaultAuthService;
import com.javaded78.timetracker.service.impl.DefaultMessageSourceService;
import com.javaded78.timetracker.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class DefaultAuthServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private DefaultMessageSourceService messageSourceService;

    @MockBean
    private DefaultUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Autowired
    private DefaultAuthService authService;

    @Test
    void login() {
        //given
        Long userId = 1L;
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest(
                username,
                password
        );
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setRoles(roles);
        Mockito.when(userService.getByUsername(username))
                .thenReturn(user);
        Mockito.when(tokenProvider.createAccessToken(userId, username, roles))
                .thenReturn(accessToken);
        Mockito.when(tokenProvider.createRefreshToken(userId, username))
                .thenReturn(refreshToken);
        //when
        JwtResponse response = authService.login(request);
        //then
        Mockito.verify(authenticationManager)
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );
        assertEquals(username, response.username());
        assertEquals(userId, response.id());
        assertNotNull(response.accessToken());
        assertNotNull(response.refreshToken());
    }

    @Test
    void loginWithIncorrectUsername() {
        //given
        String username = "username";
        String password = "password";
        JwtRequest request = new JwtRequest(
                username,
                password
        );
        User user = new User();
        user.setUsername(username);
        Mockito.when(userService.getByUsername(username))
                .thenThrow(ResourceNotFoundException.class);
        Mockito.verifyNoInteractions(tokenProvider);
        //when

        //then
        assertThrows(ResourceNotFoundException.class,
                () -> authService.login(request));
    }

    @Test
    void refresh() {
        //given
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse response = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
        Mockito.when(tokenProvider.refreshUserTokens(refreshToken))
                .thenReturn(response);
        //when
        JwtResponse testResponse = authService.refresh(refreshToken);
        //then
        Mockito.verify(tokenProvider).refreshUserTokens(refreshToken);
        assertEquals(testResponse, response);
    }
}
