package com.javaded78.timetracker.dto.user;

public record UserRegistrationRequest(

        String username,
        String email,
        String password
) {
}
