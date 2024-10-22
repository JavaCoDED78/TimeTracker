package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.dto.auth.JwtRequest;
import com.javaded78.timetracker.dto.auth.JwtResponse;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.security.JwtTokenProvider;
import com.javaded78.timetracker.service.AuthService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password())
        );

        User user = userService.getByUsername(loginRequest.username());
        return new JwtResponse(
                user.getId(),
                user.getUsername(),
                jwtTokenProvider.createAccessToken(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles()
                ),
                jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername())
        );
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
