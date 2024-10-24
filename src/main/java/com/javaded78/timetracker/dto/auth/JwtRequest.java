package com.javaded78.timetracker.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for login")
public record JwtRequest(

        @Schema(
                description = "email",
                example = "johndoe@gmail.com"
        )
        @Email(message = "{user.email.invalid}")
        @NotBlank(message = "{user.email.notnull}")
        String username,

        @Schema(
                description = "password",
                example = "qwerty"
        )
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{user.password.size}")
        @NotBlank(message = "{user.password.notnull}")
        String password
        ) {
}
