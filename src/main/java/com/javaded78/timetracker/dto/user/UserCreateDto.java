package com.javaded78.timetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreateDto(

        @Email(message = "{user.email.invalid}")
        @NotBlank(message = "{user.email.notnull}")
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{user.password.size}")
        @NotBlank(message = "{user.password.notnull}")
        String password,

        @Size(min = 5, max = 50, message = "{user.firstname.size}")
        @NotBlank(message = "{user.firstname.notnull}")
        String firstname,

        @Size(min = 5, max = 50, message = "{user.lastname.size}")
        @NotBlank(message = "{user.lastname.notnull}")
        String lastname) {
}
