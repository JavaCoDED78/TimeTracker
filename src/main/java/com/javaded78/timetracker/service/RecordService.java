package com.javaded78.timetracker.service;

import com.javaded78.timetracker.model.Record;

public interface RecordService {

    Record add(Record record);

    Record update(Record record);

    Record getByTaskId(Long taskId);

    void delete(Long id);
}
