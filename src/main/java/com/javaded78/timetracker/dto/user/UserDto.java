package com.javaded78.timetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaded78.timetracker.dto.validation.OnCreate;
import com.javaded78.timetracker.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDto(

        @NotNull(message = "{id.notnull}", groups = OnUpdate.class)
        Long id,

        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String email,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{password.size}", groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "{password.notnull}")
        String password,

        @Size(min = 5, max = 50, message = "{username.size}",
                groups = {OnCreate.class, OnUpdate.class}
        )
        @NotBlank(message = "{username.notnull}")
        String username
) {
}
