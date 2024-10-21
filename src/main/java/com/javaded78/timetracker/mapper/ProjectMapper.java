package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.project.ProjectDto;
import com.javaded78.timetracker.dto.project.ProjectStateDto;
import com.javaded78.timetracker.model.Project;
import com.javaded78.timetracker.model.ProjectState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends Mappable<Project, ProjectDto> {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "timeRecords", ignore = true)
    @Override
    Project toEntity(ProjectDto dto);

    @Mapping(target = "email", source = "entity.user.email")
    @Override
    ProjectDto toDto(Project entity);

    ProjectStateDto toStateDto(Project entity, ProjectState state);
}
