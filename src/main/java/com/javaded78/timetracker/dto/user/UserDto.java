package com.javaded78.timetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaded78.timetracker.dto.validation.OnCreate;
import com.javaded78.timetracker.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @Email(message = "{email.invalid}", groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "{email.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String email,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{password.size}", groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "{password.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String password,

        @Size(min = 5, max = 50, message = "{firstname.size}",
                groups = {OnCreate.class, OnUpdate.class}
        )
        @NotBlank(message = "{firstname.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String firstname,

        @Size(min = 5, max = 50, message = "{lastname.size}",
                groups = {OnCreate.class, OnUpdate.class}
        )
        @NotBlank(message = "{lastname.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String lastname
) {
}
