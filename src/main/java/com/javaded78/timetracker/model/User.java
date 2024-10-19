package com.javaded78.timetracker.model;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_username", unique = true, nullable = false)
    private String username;

    @Column(name = "c_email", unique = true, nullable = false)
    private String email;

    @Column(name = "c_password", nullable = false)
    private String password;

    @Column(name = "c_role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

}
