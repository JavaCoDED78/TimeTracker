package com.javaded78.timetracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "RecordResponse DTO")
public record RecordDto(

        @Schema(
                description = "Time when the task started",
                example = "2024-01-01 12:00"
        )
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,

        @Schema(
                description = "Time when the task ended or null if not finished",
                example = "2024-01-01 12:00"
        )
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime endTime
) {
}
