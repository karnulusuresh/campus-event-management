package com.campus.event.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // One category can have many events
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Event> events;

    // Getters and Setters
}
