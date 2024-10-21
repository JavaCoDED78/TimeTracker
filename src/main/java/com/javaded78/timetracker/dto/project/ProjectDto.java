package com.javaded78.timetracker.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaded78.timetracker.dto.validation.OnCreate;
import com.javaded78.timetracker.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ProjectDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @Size(min = 6, max = 255, message = "{project.name.size}", groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "{project.name.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String title,

        @Size(max = 5000, message = "{project.description.size}", groups = {OnCreate.class, OnUpdate.class})
        String description,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,

        @Email(message = "{user.email.invalid}" , groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "{user.email.notnull}", groups = {OnCreate.class, OnUpdate.class})
        String email
) {
}
