package com.javaded78.timetracker.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "UserUpdate DTO")
public record UserUpdateDto(

        @Schema(
                description = "User id",
                example = "1"
        )
        @NotNull(message = "{user.id.notnull}")
        Long id,

        @Schema(
                description = "User email",
                example = "johndoe@gmail.com"
        )
        @Email(message = "{user.email.invalid}")
        @NotBlank(message = "{user.email.notnull}")
        String username,

        @Schema(
                description = "User encrypted password",
                example = "123456"
        )
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 6, max = 32, message = "{user.password.size}")
        @NotBlank(message = "{user.password.notnull}")
        String password,

        @Schema(
                description = "User firstname",
                example = "John"
        )
        @Size(min = 5, max = 50, message = "{user.firstname.size}")
        @NotBlank(message = "{user.firstname.notnull}")
        String firstname,

        @Schema(
                description = "User lastname",
                example = "Doe"
        )
        @Size(min = 5, max = 50, message = "{user.lastname.size}")
        @NotBlank(message = "{user.lastname.notnull}")
        String lastname) {
}
