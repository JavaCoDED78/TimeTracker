package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.task.TaskResponseDto;
import com.javaded78.timetracker.dto.task.TaskUpdateDto;
import com.javaded78.timetracker.mapper.TaskMapper;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Task Controller",
        description = "Task API"
)
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    @Operation(summary = "Get all tasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<TaskResponseDto>> getAll(@ParameterObject Pageable pageable) {
        log.info("Received request to get all tasks with pageable {}", pageable);
        Page<Task> tasks = taskService.getAll(pageable);
        Page<TaskResponseDto> taskDtos = tasks.map(taskMapper::toDto);
        return ResponseEntity.ok(new PaginatedResponse<>(taskDtos));
    }

    @PutMapping
    @Operation(summary = "Update task")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TaskResponseDto> update(@Validated @RequestBody TaskUpdateDto taskUpdateDto) {
        log.info("Received request to update task with dto {}", taskUpdateDto);
        Task task = taskMapper.updatedToEntity(taskUpdateDto);
        Task updatedTask = taskService.update(task);
        return ResponseEntity.ok().body(taskMapper.toDto(updatedTask));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable Long id) {
        log.info("Received request to get task by id: {}", id);
        Task task = taskService.getById(id);
        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by id")
    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Received request to delete task by id: {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    @Operation(summary = "Start time record for task")
    @PreAuthorize("@customSecurityExpression.canAccessTaskAction(#id)")
    public ResponseEntity<TaskResponseDto> start(@PathVariable Long id) {
        log.info("Received request to start time record for task with id: {}", id);
        Task task = taskService.start(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @PostMapping("/{id}/stop")
    @Operation(summary = "Stop time record for task")
    @PreAuthorize("@customSecurityExpression.canAccessTaskAction(#id)")
    public ResponseEntity<TaskResponseDto> stop(@PathVariable Long id) {
        log.info("Received request to stop time record for task with id: {}", id);
        Task task = taskService.stop(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }
}
