package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.project.ProjectDto;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    PaginatedResponse<ProjectDto> getAll(Pageable pageable);

    ProjectDto getById(Long id);

    ProjectDto add(ProjectDto projectDto);

    ProjectDto update(Long id, ProjectDto projectDto);

    void delete(Long id);
}
