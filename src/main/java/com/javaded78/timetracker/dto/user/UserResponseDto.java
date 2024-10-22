package com.javaded78.timetracker.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "UserResponse DTO")
public record UserResponseDto(
        @Schema(
                description = "User id",
                example = "1"
        )
        Long id,

        @Schema(
                description = "User email",
                example = "johndoe@gmail.com"
        )
        String username,

        @Schema(
                description = "User firstname",
                example = "John"
        )
        String firstname,

        @Schema(
                description = "User lastname",
                example = "Doe"
        )
        String lastname) {
}
