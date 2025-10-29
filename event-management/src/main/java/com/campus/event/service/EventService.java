package com.campus.event.service;

import java.util.List;

import com.campus.event.entity.Event;

public interface EventService {
	
	List<Event> getAllEvents();
	
    Event getEventById(Long id);
    
    List<Event> getEventsByCategory(Long categoryId);
    
    Event createEvent(Event event);
    
    Event updateEvent(Long id, Event event);
    
    void deleteEvent(Long id);

}
