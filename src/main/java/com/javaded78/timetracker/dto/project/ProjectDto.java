package com.javaded78.timetracker.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ProjectDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @Size(min = 6, max = 255, message = "{project.name.size}")
        @NotBlank(message = "{project.name.notnull}")
        String title,

        @Size(max = 5000, message = "{project.description.size}")
        String description,

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime start
) {
}
