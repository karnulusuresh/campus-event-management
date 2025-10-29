package com.campus.event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
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
    
    public Category() {}

    public Category(Long id, String name, String description) {
        this.categoryId = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
}
