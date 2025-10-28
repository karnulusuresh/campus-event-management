package com.campus.event.service;

import java.util.List;

import com.campus.event.entity.User;
import com.campus.event.pojo.UserRequest;

public interface UserService {
	
	User createUser(UserRequest request); 
	
	List<User> getAllUsers(); 
	
    User getUserById(Long id); 
    
    void deleteUser(Long id); 

	User updateUser(Long id, UserRequest updatedUser);

}
