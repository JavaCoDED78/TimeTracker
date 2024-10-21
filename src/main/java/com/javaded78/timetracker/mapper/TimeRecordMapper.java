package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import com.javaded78.timetracker.model.TimeRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeRecordMapper extends Mappable<TimeRecord, TimeRecordResponse> {
}
