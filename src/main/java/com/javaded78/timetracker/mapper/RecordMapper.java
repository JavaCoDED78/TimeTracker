package com.javaded78.timetracker.mapper;

import com.javaded78.timetracker.dto.RecordDto;
import com.javaded78.timetracker.model.Record;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RecordMapper extends Mappable<Record, RecordDto> {


}
