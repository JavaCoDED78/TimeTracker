package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.auth.JwtRequest;
import com.javaded78.timetracker.dto.auth.JwtResponse;
import com.javaded78.timetracker.dto.user.UserCreateDto;
import com.javaded78.timetracker.dto.user.UserResponseDto;
import com.javaded78.timetracker.mapper.UserMapper;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.service.AuthService;
import com.javaded78.timetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Auth Controller",
        description = "Auth API"
)
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody final JwtRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDto> register(
            @Validated @RequestBody final UserCreateDto userCreateDto) {
        User user = userMapper.createdToEntity(userCreateDto);
        User registeredUser = userService.register(user);
        return new ResponseEntity<>(userMapper.toDto(registeredUser), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token")
    public ResponseEntity<JwtResponse> refreshToken(
            @RequestBody final String refreshToken
    ) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}
