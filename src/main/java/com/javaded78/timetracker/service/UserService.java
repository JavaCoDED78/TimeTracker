package com.javaded78.timetracker.service;

import com.javaded78.timetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> getAll(Pageable pageable);

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User register(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    User getTaskAuthor(Long taskId);

    void delete(Long id);
}
