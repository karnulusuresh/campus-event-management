package com.campus.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campus.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findAllByOrderByStartDateTimeDesc();
	
    List<Event> findByCategory_CategoryId(Long categoryId);

	Optional<Event> findEventByTitle(String title);
	
	@Query("SELECT COUNT(e) FROM Event e")
    long countEvents();

}