package com.javaded78.timetracker.repository;

import com.javaded78.timetracker.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
}