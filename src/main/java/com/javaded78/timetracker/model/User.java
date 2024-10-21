package com.javaded78.timetracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_firstname", nullable = false)
    private String firstname;

    @Column(name = "c_lastname", nullable = false)
    private String lastname;

    @Column(name = "c_email", unique = true, nullable = false)
    private String email;

    @Column(name = "c_password", nullable = false)
    private String password;

    @Column(name = "c_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "c_role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<Project> projects = new HashSet<>();
}
