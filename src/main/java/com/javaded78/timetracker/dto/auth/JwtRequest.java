package com.javaded78.timetracker.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JwtRequest(


        @Email(message = "{user.email.invalid}")
        @NotBlank(message = "{user.email.notnull}")
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{user.password.size}")
        @NotBlank(message = "{user.password.notnull}")
        String password
        ) {
}
