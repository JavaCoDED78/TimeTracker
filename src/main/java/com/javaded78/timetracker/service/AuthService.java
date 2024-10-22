package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.auth.JwtRequest;
import com.javaded78.timetracker.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
