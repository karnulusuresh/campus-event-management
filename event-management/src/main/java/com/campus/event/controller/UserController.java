package com.campus.event.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController { 
	@PostMapping 
	public String createUser() {
		log.info("creteUser() called");
		return "";
	}
	
	@GetMapping
	public String getUsers() {
		log.info("Fethced all users");
		return "";
	}
	
	@GetMapping("/{id}")
	public String getUser(int id) {
		log.info("user called with id : {} ",id);
		return "";
	}
	
	@PutMapping("/{id}")
	public String updateUser(int id) {
		log.info("updated user with id :");
		return "";
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(int id) {
		log.info("deleteUser() called");
		return "";
	}
}
