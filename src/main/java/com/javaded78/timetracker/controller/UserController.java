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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(
        name = "User Controller",
        description = "User API"
)
@Slf4j
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userUpdateDto.id)")
    public ResponseEntity<UserResponseDto> update(@Validated @RequestBody UserUpdateDto userUpdateDto) {
        log.info("Received request to update user with {}", userUpdateDto);
        User user = userMapper.updatedToEntity(userUpdateDto);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<UserResponseDto>> getAll(Pageable pageable) {
        log.info("Received request to get all users with pageable {}", pageable);
        Page<User> users = userService.getAll(pageable);
        Page<UserResponseDto> userDtos = users.map(userMapper::toDto);
        return ResponseEntity.ok(new PaginatedResponse<>(userDtos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        log.info("Received to get user by id: {}", id);
        User user = userService.getById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received request to delete user by id {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all User tasks")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable Long id) {
        log.info("Received request to get tasks by user id {}", id);
        List<Task> tasks = taskService.getAllByUserId(id);
        return ResponseEntity.ok(taskMapper.toDto(tasks));
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "Add task to user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TaskResponseDto> createTask(@PathVariable Long id,
                                                      @Validated @RequestBody TaskCreateDto taskCreateDto
    ) {
        log.info("Received request to create task {} for user id {}", taskCreateDto, id);
        Task task = taskMapper.cratedToEntity(taskCreateDto);
        Task createdTask = taskService.create(task, id);
        return ResponseEntity.ok(taskMapper.toDto(createdTask));
    }
}
