package com.javaded78.timetracker.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaded78.timetracker.dto.validation.OnCreate;
import com.javaded78.timetracker.dto.validation.OnUpdate;
import com.javaded78.timetracker.model.ProjectState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ProjectStateDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        String title,
        String description,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Email(message = "{user.email.invalid}" , groups = {OnCreate.class})
        @NotBlank(message = "{user.email.notnull}", groups = {OnCreate.class})
        ProjectState state
) {
}
