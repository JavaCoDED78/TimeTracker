package com.javaded78.timetracker.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.javaded78.timetracker.dto.RecordDto;
import com.javaded78.timetracker.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "TaskResponse DTO")
@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = TaskResponseDto.CustomFilter.class)
public record TaskResponseDto(

        @Schema(
                description = "Title of the task",
                example = "New title"
        )
        Long id,

        @Schema(
                description = "Description of the task",
                example = "New description for the task"
        )
        String title,

        @Schema(
                description = "Time when the task expires",
                example = "2024-01-01 12:00"
        )
        String description,

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime expirationDate,

        @Schema(
                description = "Task status",
                example = "IN_PROGRESS"
        )
        Status status,

        @Schema(
                description = "Task time record", subTypes = {RecordDto.class}
        )
        RecordDto record
) {

        public static class CustomFilter {
                @Override
                public boolean equals(Object obj) {
                    return obj instanceof RecordDto dto && dto.startTime() == null;
                }
        }
}
