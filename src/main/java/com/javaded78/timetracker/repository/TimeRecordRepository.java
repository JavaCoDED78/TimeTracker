package com.javaded78.timetracker.repository;

import com.javaded78.timetracker.model.Project;
import com.javaded78.timetracker.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {

    @Query("SELECT tr FROM TimeRecord tr WHERE tr.project = :project ORDER BY tr.startTime DESC")
    TimeRecord findStartingRecord(@Param("project") Project newProject);

}