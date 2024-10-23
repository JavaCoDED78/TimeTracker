package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Record;
import com.javaded78.timetracker.repository.RecordRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultRecordService implements RecordService {

    private final RecordRepository recordRepository;
    private final MessageSourceService messageService;

    @Override
    @Transactional
    public Record add(Record record) {
        return recordRepository.save(record);
    }

    @Override
    @Transactional
    public Record update(Record record) {
        record.setEndTime(LocalDateTime.now());
        return recordRepository.save(record);
    }

    @Override
    public Record getByTaskId(Long taskId) {
        return recordRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.id.not_found", taskId))
                );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }
}
