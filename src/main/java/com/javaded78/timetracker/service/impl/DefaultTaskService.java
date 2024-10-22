package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Status;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.repository.TaskRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final MessageSourceService messageService;

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.id.not_found", id))
                );
    }

    @Override
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }


    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findAllSoonTasks(Timestamp.valueOf(now),
                Timestamp.valueOf(now.plus(duration)));
    }

    @Override
    @Transactional
    public Task update(Task task) {
        Task existing = getById(task.getId());
        if (task.getStatus() == null) {
            existing.setStatus(Status.PENDING);
        } else {
            existing.setStatus(task.getStatus());
        }
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setExpirationDate(task.getExpirationDate());
        taskRepository.save(existing);
        return existing;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }
        taskRepository.save(task);
        taskRepository.assignTask(userId, task.getId());
        return task;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
}
