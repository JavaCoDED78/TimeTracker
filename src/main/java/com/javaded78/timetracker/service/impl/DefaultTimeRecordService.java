package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import com.javaded78.timetracker.mapper.TimeRecordMapper;
import com.javaded78.timetracker.model.Project;
import com.javaded78.timetracker.model.TimeRecord;
import com.javaded78.timetracker.repository.TimeRecordRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.TimeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultTimeRecordService implements TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;
    private final TimeRecordMapper timeRecordMapper;
    private final MessageSourceService messageService;

    @Override
    @Transactional
    public PaginatedResponse<TimeRecordResponse> getAll(Pageable pageable) {
        Page<TimeRecord> all = timeRecordRepository.findAll(pageable);
        Page<TimeRecordResponse> records = all.map(timeRecordMapper::toDto);
        System.out.println();
        return new PaginatedResponse<>(records);
    }

    @Override
    @Transactional
    public TimeRecord save(TimeRecord record) {
       return timeRecordRepository.save(record);
    }

    @Override
    public TimeRecord getStaringRecord(Project newProject) {
        return timeRecordRepository.findStartingRecord(newProject);
    }
}
