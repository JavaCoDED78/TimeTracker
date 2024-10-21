package com.javaded78.timetracker.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaded78.timetracker.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record TaskCreateDto(

        @Size(min = 6, max = 255, message = "{task.name.size}")
        @NotBlank(message = "{task.name.notnull}")
        String title,

        @Size(max = 5000, message = "{task.description.size}")
        String description,

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime expirationDate,

        Status status
) {
}
