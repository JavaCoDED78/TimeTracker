package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.task.TaskCreateDto;
import com.javaded78.timetracker.dto.task.TaskResponseDto;
import com.javaded78.timetracker.dto.user.UserResponseDto;
import com.javaded78.timetracker.dto.user.UserUpdateDto;
import com.javaded78.timetracker.mapper.TaskMapper;
import com.javaded78.timetracker.mapper.UserMapper;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.model.User;
import com.javaded78.timetracker.service.TaskService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userUpdateDto.id)")
    public ResponseEntity<UserResponseDto> update(@Validated @RequestBody UserUpdateDto userUpdateDto) {
        User user = userMapper.updatedToEntity(userUpdateDto);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<UserResponseDto>> getAll(Pageable pageable) {
        Page<User> users = userService.getAll(pageable);
        Page<UserResponseDto> userDtos = users.map(userMapper::toDto);
        return ResponseEntity.ok(new PaginatedResponse<>(userDtos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return ResponseEntity.ok(taskMapper.toDto(tasks));
    }

    @PostMapping("/{id}/tasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TaskResponseDto> createTask(@PathVariable Long id, @Validated @RequestBody TaskCreateDto taskCreateDto) {
        Task task = taskMapper.cratedToEntity(taskCreateDto);
        Task createdTask = taskService.create(task, id);
        return ResponseEntity.ok(taskMapper.toDto(createdTask));
    }
}

