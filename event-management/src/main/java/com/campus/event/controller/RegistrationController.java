package com.campus.event.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/registrations")
@RestController
@Slf4j
public class RegistrationController {
	@PostMapping 
	public String createRegistration() {
		log.info("createRegistration() called");
		return "";
	}
	
	@GetMapping
	public String getAllRegistrations() {
		log.info("getAllRegistrations() called");
		return "";
	}
	
	@GetMapping("/user/{userId}")
	public String getRegistrationsById(int userId) {
		log.info("getRegistrationsById called with id : {} ",userId);
		return "";
	}
	
	@PutMapping("/{id}")
	public String updateRegistration(int id) {
		log.info("updateRegistration called with id : {} ",id);
		return "";
	}
	
	@DeleteMapping("/{id}")
	public String deleteRegistration(int id) { 
		log.info("deleteRegistration() called called with id : {} ",id);
		return "";
	}


}
