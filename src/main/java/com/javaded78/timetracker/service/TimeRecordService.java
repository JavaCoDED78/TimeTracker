package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import com.javaded78.timetracker.model.Project;
import com.javaded78.timetracker.model.TimeRecord;
import org.springframework.data.domain.Pageable;

public interface TimeRecordService {

    PaginatedResponse<TimeRecordResponse> getAll(Pageable pageable);

    TimeRecord save(TimeRecord record);

    TimeRecord getStaringRecord(Project newProject);
}
