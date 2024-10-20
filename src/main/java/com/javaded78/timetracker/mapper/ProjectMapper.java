package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.project.ProjectDto;
import com.javaded78.timetracker.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends Mappable<Project, ProjectDto> {
}
