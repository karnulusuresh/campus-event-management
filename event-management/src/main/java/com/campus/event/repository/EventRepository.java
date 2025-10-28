package com.campus.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategory_CategoryId(Long categoryId);
}