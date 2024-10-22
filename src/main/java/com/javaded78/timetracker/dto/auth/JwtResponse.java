package com.javaded78.timetracker.dto.auth;

import lombok.Builder;

@Builder
public record JwtResponse(Long id,
                          String username,
                          String accessToken,
                          String refreshToken
) {
}
