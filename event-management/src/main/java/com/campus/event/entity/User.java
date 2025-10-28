package com.campus.event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository; 

@Entity
@Table(name = "users")
@Data
@Repository
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;

    private LocalDateTime createdAt = LocalDateTime.now();

    // One user can register for many events
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    // Enum for role
    public enum Role {
        STUDENT, ADMIN
    }

    // Getters and Setters
}
