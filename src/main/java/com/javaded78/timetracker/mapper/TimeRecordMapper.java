package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import com.javaded78.timetracker.model.TimeRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimeRecordMapper extends Mappable<TimeRecord, TimeRecordResponse> {

    @Override
    @Mapping(target = "project.email", source = "entity.project.user.email")
    TimeRecordResponse toDto(TimeRecord entity);

    @Override
    TimeRecord toEntity(TimeRecordResponse dto);
}
