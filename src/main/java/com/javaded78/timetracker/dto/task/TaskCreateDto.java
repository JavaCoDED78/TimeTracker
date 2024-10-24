package com.javaded78.timetracker.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "TaskCreate DTO")
public record TaskCreateDto(

        @Schema(
                description = "Title of the task",
                example = "New title"
        )
        @Size(min = 6, max = 255, message = "{task.name.size}")
        @NotBlank(message = "{task.name.notnull}")
        String title,

        @Schema(
                description = "Description of the task",
                example = "New description for the task"
        )
        @Size(max = 5000, message = "{task.description.size}")
        String description,

        @Schema(
                description = "Time when the task expires",
                example = "2024-01-01 12:00"
        )
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime expirationDate
) {
}
