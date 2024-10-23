package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Record;
import com.javaded78.timetracker.model.Status;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.repository.TaskRepository;
import com.javaded78.timetracker.service.MessageSourceService;
import com.javaded78.timetracker.service.RecordService;
import com.javaded78.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final MessageSourceService messageService;
    private final RecordService recordService;

    @Override
    public Task getById(Long id) {
         Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.entity.id.not_found", id))
                );
         log.info("Task with id: {} found", id);
         return task;
    }

    @Override
    public List<Task> getAllByUserId(Long id) {
        List<Task> tasks = taskRepository.findAllByUserId(id);
        log.info("Tasks for user with id: {} found", id);
        return tasks;
    }


    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        List<Task> tasks = taskRepository.findAllSoonTasks(Timestamp.valueOf(now),
                Timestamp.valueOf(now.plus(duration)));
        log.info("Tasks with soon expiration date found");
        return tasks;
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
        log.info("Task with id: {} updated", task.getId());
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
        log.info("Task with id: {} for user with id: {} created", task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
        log.info("Task with id: {} deleted", id);
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        log.info("Tasks with pageable: {} found", pageable);
        return tasks;
    }

    @Override
    @Transactional
    public Task start(Long id) {
        Task task = getByIdAndStatus(id, Status.PENDING);
        task.setStatus(Status.IN_PROGRESS);
        Record record = new Record();
        record.setTask(task);
        record.setStartTime(LocalDateTime.now());
        recordService.add(record);
        Task started = taskRepository.save(task);
        log.info("Task with id: {} started time record", id);
        return started;
    }

    @Override
    @Transactional
    public Task stop(Long id) {
        Task task = getByIdAndStatus(id, Status.IN_PROGRESS);
        Record record = recordService.getByTaskId(task.getId());
        recordService.update(record);
        task.setStatus(Status.DONE);
        Task stopped = taskRepository.save(task);
        log.info("Task with id: {} stopped time record", id);
        return stopped;
    }

    @Override
    public Task getByIdAndStatus(Long id, Status status) {
        Task task = taskRepository.findByIdAndStatus(id, status)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.generateMessage("error.task.not_found", id))
                );
        log.info("Task with id: {} and status: {} found", id, status);
        return task;
    }
}
