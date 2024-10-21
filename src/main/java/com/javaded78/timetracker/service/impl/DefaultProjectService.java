package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.project.ProjectDto;
import com.javaded78.timetracker.dto.project.ProjectStateDto;
import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.mapper.ProjectMapper;
import com.javaded78.timetracker.model.Project;
import com.javaded78.timetracker.model.Status;
import com.javaded78.timetracker.model.TimeRecord;
import com.javaded78.timetracker.repository.ProjectRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.ProjectService;
import com.javaded78.timetracker.service.TimeRecordService;
import com.javaded78.timetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultProjectService implements ProjectService {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final MessageSourceService messageService;
    private final TimeRecordService timeRecordService;

    @Override
    public PaginatedResponse<ProjectDto> getAll(Pageable pageable) {
        Page<ProjectDto> projects = projectRepository.findAll(pageable)
                .map(projectMapper::toDto);
        return new PaginatedResponse<>(projects);
    }

    @Override
    public ProjectDto getById(Long id) {
        return projectMapper.toDto(getProjectById(id));
    }

    private Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.not_found", id))
                );
    }

    @Override
    @Transactional
    public ProjectDto add(ProjectDto projectDto) {
        Project newProject = projectMapper.toEntity(projectDto);
        newProject.setUser(userService.getUserByEmail(projectDto.email()));
        newProject.setCreatedAt(LocalDateTime.now());
        return projectMapper.toDto(projectRepository.saveAndFlush(newProject));
    }

    @Override
    @Transactional
    public ProjectDto update(Long id, ProjectDto projectDto) {
        Project existingProject = getProjectById(id);
        existingProject.setTitle(projectDto.title());
        existingProject.setDescription(projectDto.description());
        return projectMapper.toDto(projectRepository.saveAndFlush(existingProject));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public ProjectStateDto start(Long id) {
        Project newProject = getProjectById(id);
        TimeRecord record = new TimeRecord();
        record.setProject(newProject);
        record.setStatus(Status.ONGOING);
        record.setStartTime(LocalDateTime.now());
        TimeRecord savedTimeRecord = timeRecordService.save(record);
        return projectMapper.toStateDto(newProject, savedTimeRecord.getStatus());
    }

    @Override
    @Transactional
    public ProjectStateDto stop(Long id) {
        Project newProject = getProjectById(id);
        TimeRecord record = timeRecordService.getStaringRecord(newProject);
        record.setEndTime(LocalDateTime.now());
        record.setStatus(Status.USER_STOPPED);
        timeRecordService.save(record);
        return projectMapper.toStateDto(record.getProject(), Status.USER_STOPPED);
    }

}
