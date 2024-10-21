package com.javaded78.timetracker.controller;

import com.javaded78.timetracker.dto.PaginatedResponse;
import com.javaded78.timetracker.dto.record.TimeRecordResponse;
import com.javaded78.timetracker.service.TimeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/records")
@RequiredArgsConstructor
public class TimeRecordController {

    private final TimeRecordService timeRecordService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<TimeRecordResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(timeRecordService.getAll(pageable));
    }
}
