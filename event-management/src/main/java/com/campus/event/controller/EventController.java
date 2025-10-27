package com.campus.event.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventController {
	@PostMapping 
	public String createEvent() {
		log.info("createEvent() called");
		return "";
	}
	
	@GetMapping
	public String getAllEvents() {
		log.info("getAllEvents() called");
		return "";
	}
	
	@GetMapping("/{id}")
	public String getEventById(int id) {
		log.info("getEventsById() called with id : {} ",id);
		return "";
	}
	
	@GetMapping("/category/{categoryId}")
	public String getEventByCategory(int categoryId) {
		log.info("getEventByCategory() called with id : {} ",categoryId);
		return "";
	}
	
	@PutMapping("/{id}")
	public String updateEventById(int id) {
		log.info("updateEventById() called with id : {} ",id);
		return "";
	}
	
	@DeleteMapping("/{id}")
	public String deleteEventById(int id) { 
		log.info("deleteEvent() called called with id : {} ",id);
		return "";
	}

}
