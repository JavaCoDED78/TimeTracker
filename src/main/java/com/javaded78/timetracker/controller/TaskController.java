package com.javaded78.timetracker.controller;


import com.javaded78.timetracker.dto.task.TaskResponseDto;
import com.javaded78.timetracker.dto.task.TaskUpdateDto;
import com.javaded78.timetracker.mapper.TaskMapper;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    public ResponseEntity<TaskResponseDto> update(@Validated @RequestBody TaskUpdateDto taskUpdateDto) {
        Task task = taskMapper.updatedToEntity(taskUpdateDto);
        Task updatedTask = taskService.update(task);
        return ResponseEntity.ok().body(taskMapper.toDto(updatedTask));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
