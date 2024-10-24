package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.task.TaskCreateDto;
import com.javaded78.timetracker.dto.task.TaskResponseDto;
import com.javaded78.timetracker.dto.task.TaskUpdateDto;
import com.javaded78.timetracker.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskResponseDto> {

    @Mapping(target = "record", ignore = true)
    @Mapping(target = "status", ignore = true)
    Task updatedToEntity(TaskUpdateDto taskUpdateDto);

    @Mapping(target = "record", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    Task cratedToEntity(TaskCreateDto taskCreateDto);
}
