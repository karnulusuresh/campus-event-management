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

import com.campus.event.dto.RegistrationDTO;
import com.campus.event.pojo.RegistrationRequest;
import com.campus.event.service.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/registrations")
@RestController
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
	
	private final RegistrationService regService;
	@PostMapping 
	public RegistrationDTO createRegistration(@RequestBody RegistrationRequest request) {
		log.info("createRegistration() called");
		RegistrationDTO response = regService.registerUserForEvent(request);
		log.info("created registration for User : {}",response);
		return response;
	}
	
	@GetMapping
	public List<RegistrationDTO> getAllRegistrations() {
		log.info("getAllRegistrations() called");
		List<RegistrationDTO> response =regService.getAllRegistrations();
		log.info("fetched all registrations succesfully.....");
		return response;
	}
	
	@GetMapping("/user/{userId}")
	public List<RegistrationDTO> getRegistrationsByUser(@PathVariable Long userId) {
		log.info("getRegistrationsByUser called with id : {} ",userId);
		List<RegistrationDTO> response = regService.getRegistrationsByUser(userId);
		log.info("Retrieved response by Id as : {}",response);
		return response;
	}
	
	@GetMapping("/event/{eventId}")
	public List<RegistrationDTO> getRegistrationsByEvent(@PathVariable Long eventId) {
		log.info("getRegistrationsByEvent called with id : {} ",eventId);
		List<RegistrationDTO> response = regService.getRegistrationsByEvent(eventId);
		log.info("Retrieved response by Id as : {}",response);
		return response;
	}
	
	@GetMapping("registration-count")
	public long count() {
		log.info("calling registrations service for count..");
		long count = regService.countRegistrations();
		log.info("retrieved registrations count");
		return count;
	}
	
	@GetMapping("pending-count")
	public long pendingCount() {
		log.info("pending count method was calling");
		long count = regService.countPendingApprovals();
		log.info("Retrieve peding count");
		return count;
	}
	
	@DeleteMapping("/{regId}/cancel/{userId}")
	public RegistrationDTO cancelRegistration(@PathVariable Long regId,@PathVariable Long userId) { 
		log.info("cancelRegistration() called called with userId : {} ",userId);
		RegistrationDTO response =  regService.cancelRegistration(regId, userId);
		log.info("Cancelled registration successfully....");
		return response;
	}

	@DeleteMapping("/{eventId}")
	public void deleteAll(@PathVariable Long eventId) {
		regService.deleteByEventId(eventId);
	}
}
