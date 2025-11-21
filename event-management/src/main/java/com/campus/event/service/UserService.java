package com.campus.event.service;

import java.util.List;

import com.campus.event.dto.UserDTO;
import com.campus.event.pojo.UserRequest;

public interface UserService {
	
	UserDTO createUser(UserRequest request); 
	
	List<UserDTO> getAllUsers(); 
	
    UserDTO getUserById(Long id); 
    
    void deleteUser(Long id); 

	UserDTO updateUser(Long id, UserRequest updatedUser);
	
    long countUsers();

}
