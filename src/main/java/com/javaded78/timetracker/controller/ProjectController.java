package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.project.ProjectDto;
import com.javaded78.timetracker.dto.project.ProjectStateDto;
import com.javaded78.timetracker.dto.validation.OnCreate;
import com.javaded78.timetracker.dto.validation.OnUpdate;
import com.javaded78.timetracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ProjectDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> add(@Validated(OnCreate.class) @RequestBody ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.add(projectDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.update(id, projectDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ProjectStateDto> start(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.start(id));


    }    @PostMapping("/{id}/stop")
    public ResponseEntity<ProjectStateDto> stop(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.stop(id));
    }
}
