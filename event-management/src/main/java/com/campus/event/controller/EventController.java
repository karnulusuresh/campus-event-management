package com.campus.event.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus.event.dto.EventDTO;
import com.campus.event.pojo.EventRequest;
import com.campus.event.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/events")
@Slf4j
@RequiredArgsConstructor
public class EventController { 
	
	private final EventService eventService;
	
	@PostMapping 
	public EventDTO createEvent(@RequestBody EventRequest eventRequest) { 
		log.info("createEvent() called");
		EventDTO response = eventService.createEvent(eventRequest);
		log.info("response obtained successfully as : {}",response);
		return response;
	}
	
	@GetMapping
	public List<EventDTO> getAllEvents() {
		log.info("getAllEvents() called");
		List<EventDTO> response = eventService.getAllEvents();
		log.info("response recieved from getAllEvents() successfully");
		return response;
	}
	
	@GetMapping("/{id}")
	public EventDTO getEventById(@PathVariable Long id) {
		log.info("getEventsById() called with id : {} ",id);
		EventDTO response = eventService.getEventById(id);
		log.info("retrieved response as : {}",response);
		return response; 
	}
	
	@GetMapping("/category/{categoryId}")
	public List<EventDTO> getEventByCategory(@PathVariable Long categoryId) {
		log.info("getEventByCategory() called with id : {} ",categoryId);
		List<EventDTO> response = eventService.getEventsByCategory(categoryId); 
		log.info("retrieved response as : {}",response);
		return response;
	}
	
	@PutMapping("/{id}")
	public EventDTO updateEventById(@PathVariable Long id, @RequestBody EventRequest request) {
		log.info("updateEventById() called with id : {} ",id);
		EventDTO response =eventService.updateEvent( id, request);
		log.info("retrieved response as : {}",response);
		return response; 
	}
	
	@DeleteMapping("/{id}")
	public String deleteEventById(@PathVariable Long id) { 
		log.info("deleteEvent() called called with id : {} ",id);
		String response =eventService.deleteEvent(id);
		return response;
	}
	
	@DeleteMapping("/all")
	public String deleteAll() {
		log.info("deleting all events ");
		String response = eventService.deleteAllEvents();
		log.info("deleted all events...");
		return response;
	}

}
