package com.campus.event.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
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
