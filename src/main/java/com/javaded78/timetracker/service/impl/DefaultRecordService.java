package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Record;
import com.javaded78.timetracker.repository.RecordRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultRecordService implements RecordService {

    private final RecordRepository recordRepository;
    private final MessageSourceService messageService;

    @Override
    @Transactional
    public Record add(Record record) {
        Record saved = recordRepository.save(record);
        log.info("Record saved with id: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Record update(Record record) {
        record.setEndTime(LocalDateTime.now());
        Record updated = recordRepository.save(record);
        log.info("Record updated with id: {}", updated.getId());
        return updated;
    }

    @Override
    public Record getByTaskId(Long taskId) {
        Record record = recordRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.id.not_found", taskId))
                );
        log.info("Record for task with id: {} found with id: {}",taskId, record.getId());
        return record;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        recordRepository.deleteById(id);
        log.info("Record deleted with id: {}", id);
    }
}
