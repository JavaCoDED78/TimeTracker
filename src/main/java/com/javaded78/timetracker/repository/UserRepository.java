package com.javaded78.timetracker.repository;

import com.javaded78.timetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}