package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.auth.JwtRequest;
import com.javaded78.timetracker.dto.auth.JwtResponse;
import com.javaded78.timetracker.dto.user.UserCreateDto;
import com.javaded78.timetracker.dto.user.UserResponseDto;
import com.javaded78.timetracker.mapper.UserMapper;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.service.AuthService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody final JwtRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Validated @RequestBody final UserCreateDto userCreateDto) {
        User user = userMapper.createdToEntity(userCreateDto);
        User registeredUser = userService.register(user);
        return new ResponseEntity<>(userMapper.toDto(registeredUser), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(
            @RequestBody final String refreshToken
    ) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}
