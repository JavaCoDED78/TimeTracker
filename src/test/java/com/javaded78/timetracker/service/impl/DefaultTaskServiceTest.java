package com.javaded78.timetracker.service.impl;

import com.javaded78.timetracker.config.TestConfig;
import com.javaded78.timetracker.exception.ResourceNotFoundException;
import com.javaded78.timetracker.model.Status;
import com.javaded78.timetracker.model.Task;
import com.javaded78.timetracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class DefaultTaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private DefaultTaskService taskService;

    @Test
    void getById() {
        //given
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        //when
        Task testTask = taskService.getById(id);
        //then
        Mockito.verify(taskRepository).findById(id);
        assertEquals(task, testTask);
    }

    @Test
    void getByNotExistingId() {
        //given
        Long id = 1L;
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.empty());
        //then
        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(id));
        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        //given
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }
        Mockito.when(taskRepository.findAllByUserId(userId))
                .thenReturn(tasks);
        //when
        List<Task> testTasks = taskService.getAllByUserId(userId);
        //then
        Mockito.verify(taskRepository).findAllByUserId(userId);
        assertEquals(tasks, testTasks);
    }

    @Test
    void getSoonTasks() {
        //given
        Duration duration = Duration.ofHours(1);
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }
        Mockito.when(taskRepository.findAllSoonTasks(Mockito.any(), Mockito.any()))
                .thenReturn(tasks);
        //when
        List<Task> testTasks = taskService.getAllSoonTasks(duration);
        //then
        Mockito.verify(taskRepository)
                .findAllSoonTasks(Mockito.any(), Mockito.any());
        assertEquals(tasks, testTasks);
    }

    @Test
    void update() {
        //given
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        task.setStatus(Status.DONE);
        Mockito.when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));
        //when
        Task testTask = taskService.update(task);
        //then
        Mockito.verify(taskRepository).save(task);
        assertEquals(task, testTask);
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(
                task.getDescription(),
                testTask.getDescription()
        );
        assertEquals(task.getStatus(), testTask.getStatus());
    }

    @Test
    void updateWithEmptyStatus() {
        //given
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        Mockito.when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));
        Task testTask = taskService.update(task);
        Mockito.verify(taskRepository).save(task);
        //then
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(
                task.getDescription(),
                testTask.getDescription()
        );
        assertEquals(testTask.getStatus(), Status.PENDING);
    }

    @Test
    void create() {
        //given
        Long userId = 1L;
        Long taskId = 1L;
        Task task = new Task();
        Mockito.doAnswer(invocation -> {
                    Task savedTask = invocation.getArgument(0);
                    savedTask.setId(taskId);
                    return savedTask;
                })
                .when(taskRepository).save(task);
        //when
        Task testTask = taskService.create(task, userId);
        //then
        Mockito.verify(taskRepository).save(task);
        assertNotNull(testTask.getId());
        Mockito.verify(taskRepository).assignTask(userId, task.getId());
    }

    @Test
    void delete() {
        //given
        Long id = 1L;
        //when
        taskService.delete(id);
        //then
        Mockito.verify(taskRepository).deleteById(id);
    }
}
