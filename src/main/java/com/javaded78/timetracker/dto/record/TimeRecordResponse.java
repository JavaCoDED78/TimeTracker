package com.javaded78.timetracker.dto.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaded78.timetracker.dto.project.ProjectDto;
import com.javaded78.timetracker.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record TimeRecordResponse(
        Long id,
        ProjectDto project,
        Status status,

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startTime,

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")

        LocalDateTime endTime
) {
}