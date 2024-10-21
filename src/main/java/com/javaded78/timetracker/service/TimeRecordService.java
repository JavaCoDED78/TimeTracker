package com.javaded78.timetracker.service;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import org.springframework.data.domain.Pageable;

public interface TimeRecordService {

    PaginatedResponse<TimeRecordResponse> getAll(Pageable pageable);
}
