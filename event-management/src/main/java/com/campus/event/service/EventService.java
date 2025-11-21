package com.campus.event.service;

import java.util.List;

import com.campus.event.dto.EventDTO;
import com.campus.event.pojo.EventRequest; 

public interface EventService {
	
	List<EventDTO> getAllEvents();
	
	EventDTO getEventById(Long id);
    
	List<EventDTO> getEventsByCategory(Long categoryId);
    
	EventDTO createEvent(EventRequest eventRequest);
    
	EventDTO updateEvent(Long id, EventRequest eventRequest);
    
    String  deleteEvent(Long id);
    
    public String deleteEventById(Long eventId);
    
    public long countEvents();

}
