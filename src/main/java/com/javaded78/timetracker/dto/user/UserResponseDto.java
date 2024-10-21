package com.javaded78.timetracker.dto.user;

import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String username,
        String firstname,
        String lastname) {
}
