package com.javaded78.timetracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_project")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Project implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_title", nullable = false)
    private String title;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_start")
    private LocalDateTime start;
}
