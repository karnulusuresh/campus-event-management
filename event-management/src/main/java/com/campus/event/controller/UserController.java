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

import com.campus.event.dto.UserDTO;
import com.campus.event.pojo.UserRequest;
import com.campus.event.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {  
	
	private final UserService userService;
	
	@PostMapping 
	public UserDTO createUser( @RequestBody UserRequest request) {
		log.info("createUser() called");
		UserDTO response = userService.createUser(request);
		log.info("created user with details: {}",response);
		return response;
	}
	
	@GetMapping
	public List<UserDTO> getAllUsers() {
		log.info("getAllUsers() called");
		List<UserDTO> response = userService.getAllUsers();
		log.info("fetched all users successfully..");
		return response; 
	}
	
	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable Long id) {
		log.info("getUserById() called with id : {} ",id);
		UserDTO response = userService.getUserById(id);
		log.info("recieved response : {}",response);
		return response;
	}
	
	@PutMapping("/{id}")
	public UserDTO updateUserById(@PathVariable Long id,@RequestBody UserRequest updatedUser) {
		log.info("updateUserById() called with id : {}",id);
		UserDTO response = userService.updateUser(id, updatedUser);
		log.info("recieved response from the updateUser as : {}",response);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public String deleteUserById(@PathVariable Long id) {
		log.info("deleteUserById() called");
		userService.deleteUser(id);
		log.info("User deleted  with id : {}",id);
		return "User Deleted"; 
	}
	
	@PostConstruct
	public void construct() {
		log.info("initiated UserController.......");
	}
}
