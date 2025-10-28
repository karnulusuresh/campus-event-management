package com.campus.event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "categories")
@Data
@Repository
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
