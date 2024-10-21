package com.javaded78.timetracker.service;

import com.javaded78.timetracker.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    List<Task> getAllSoonTasks(Duration duration);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);
}
