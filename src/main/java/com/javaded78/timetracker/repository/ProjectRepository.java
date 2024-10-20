package com.javaded78.timetracker.repository;

import com.javaded78.timetracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}