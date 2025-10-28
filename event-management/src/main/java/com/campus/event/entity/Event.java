package com.campus.event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "events")
@Data
@Repository
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String title;
    private String description;
    private LocalDate eventDate;
    private String location;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Many events belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // One event can have many registrations
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    // Getters and Setters
}

