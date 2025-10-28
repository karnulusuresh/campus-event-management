package com.campus.event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "registrations")
@Data
@Repository
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    private LocalDateTime registrationDate = LocalDateTime.now();

    // Many registrations belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many registrations belong to one event
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Getters and Setters
}
