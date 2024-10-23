package com.javaded78.timetracker.repository;

import com.javaded78.timetracker.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findByTaskId(Long taskId);
}